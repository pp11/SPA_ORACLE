package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.model.TerritoryInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TerritoryInfoRepository {
    private final JdbcTemplate jdbcTemplate;
    public TerritoryInfoModel saveOrUpdate(TerritoryInfoModel territoryInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM TERRITORY_INFO WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{territoryInfoModel.getId()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE TERRITORY_INFO SET  TERRITORY_NAME = ?, STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ID = ?";
            jdbcTemplate.update(updateSql,
                    territoryInfoModel.getTerritoryName(),
                    territoryInfoModel.getStatus(),
                    territoryInfoModel.getUpdateBy(),
                    territoryInfoModel.getUpdateTerminal(),
                    territoryInfoModel.getUpdateDate(),
                    territoryInfoModel.getId());
        } else {
            String insertSql = "INSERT INTO TERRITORY_INFO (ID, TERRITORY_CODE, TERRITORY_NAME, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (TERRITORY_INFO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generateNextTerrCode(),
                    territoryInfoModel.getTerritoryName(),
                    territoryInfoModel.getStatus(),
                    territoryInfoModel.getCreateBy(),
                    territoryInfoModel.getCreateTerminal(),
                    territoryInfoModel.getCreateDate(),
                    territoryInfoModel.getUpdateBy(),
                    territoryInfoModel.getUpdateTerminal(),
                    territoryInfoModel.getUpdateDate());
        }
        return territoryInfoModel;
    }

    public String generateNextTerrCode() {

        String sql = "SELECT MAX(TERRITORY_CODE) FROM TERRITORY_INFO WHERE TERRITORY_CODE LIKE 'T%'";
        String lastCode = jdbcTemplate.queryForObject(sql, String.class);
        int nextCodeNumber = 1;
        if (lastCode != null && lastCode.length() > 1) {
            String numericPart = lastCode.substring(1);
            try {
                int lastCodeNumber = Integer.parseInt(numericPart);
                nextCodeNumber = lastCodeNumber + 1;
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid format for TERRITORY_CODE in database: " + lastCode);
            }
        }
        return String.format("T%02d", nextCodeNumber);
    }

    public List<TerritoryInfoModel> findAll() {
        String sql = "SELECT * FROM TERRITORY_INFO";
        return jdbcTemplate.query(sql, new TerritoryInfoRepository.TerritoryInfoRowMapper());
    }

    private static class TerritoryInfoRowMapper implements RowMapper<TerritoryInfoModel> {
        @Override
        public TerritoryInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            TerritoryInfoModel model = new TerritoryInfoModel();
            model.setId(rs.getLong("ID"));
            model.setTerritoryCode(rs.getString("TERRITORY_CODE"));
            model.setTerritoryName(rs.getString("TERRITORY_NAME"));
            model.setStatus(rs.getString("STATUS"));
            model.setCreateBy(rs.getString("CREATE_BY"));
            model.setCreateTerminal(rs.getString("CREATE_TERMINAL"));
//            model.setUpdateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            model.setUpdateBy(rs.getString("UPDATE_BY"));
            model.setUpdateTerminal(rs.getString("UPDATE_TERMINAL"));
//            model.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());

            Timestamp createDateTimestamp = rs.getTimestamp("CREATE_DATE");
            if (createDateTimestamp != null) {
                model.setCreateDate(createDateTimestamp.toLocalDateTime());
            }

            Timestamp updateDateTimestamp = rs.getTimestamp("UPDATE_DATE");
            if (updateDateTimestamp != null) {
                model.setUpdateDate(updateDateTimestamp.toLocalDateTime());
            }
            return model;
        }
    }

    public List<TerritoryInfoModel> findByAnyData(TerritoryInfoModel criteria) {
        // Base SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM TERRITORY_INFO WHERE 1=1");

        // List to hold parameter values
        List<Object> params = new ArrayList<>();

        // Build SQL query based on non-null fields in the criteria model
        if (criteria.getId() != null) {
            sql.append(" AND ID = ?");
            params.add(criteria.getId());
        }
        if (criteria.getTerritoryCode() != null) {
            sql.append(" AND TERRITORY_CODE = ?");
            params.add(criteria.getTerritoryCode());
        }
        if (criteria.getTerritoryName() != null) {
            sql.append(" AND TERRITORY_NAME LIKE ?");
            params.add("%" + criteria.getTerritoryName() + "%");
        }
        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }
        if (criteria.getCreateBy() != null) {
            sql.append(" AND CREATE_BY = ?");
            params.add(criteria.getCreateBy());
        }
        if (criteria.getCreateTerminal() != null) {
            sql.append(" AND CREATE_TERMINAL = ?");
            params.add(criteria.getCreateTerminal());
        }
        if (criteria.getCreateDate() != null) {
            sql.append(" AND CREATE_DATE = ?");
            params.add(criteria.getCreateDate());
        }
        if (criteria.getUpdateBy() != null) {
            sql.append(" AND UPDATE_BY = ?");
            params.add(criteria.getUpdateBy());
        }
        if (criteria.getUpdateTerminal() != null) {
            sql.append(" AND UPDATE_TERMINAL = ?");
            params.add(criteria.getUpdateTerminal());
        }
        if (criteria.getUpdateDate() != null) {
            sql.append(" AND UPDATE_DATE = ?");
            params.add(criteria.getUpdateDate());
        }

        // Execute the query with the constructed SQL and parameters
        return jdbcTemplate.query(sql.toString(), params.toArray(), new TerritoryInfoRepository.TerritoryInfoRowMapper());
    }


}

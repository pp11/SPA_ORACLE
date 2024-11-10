package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.RegionInfoModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegionInfoRepository {
    private final JdbcTemplate jdbcTemplate;

    public RegionInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RegionInfoModel saveOrUpdate(RegionInfoModel regionInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM REGION_INFO WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{regionInfoModel.getId()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE REGION_INFO SET  REGION_NAME = ?, STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ID = ?";
            jdbcTemplate.update(updateSql,
                    regionInfoModel.getRegionName(),
                    regionInfoModel.getStatus(),
                    regionInfoModel.getUpdateBy(),
                    regionInfoModel.getUpdateTerminal(),
                    regionInfoModel.getUpdateDate(),
                    regionInfoModel.getId());
        } else {
            String insertSql = "INSERT INTO REGION_INFO (ID, REGION_CODE, REGION_NAME, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (REGION_INFO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generateNextRegionCode(),
                    regionInfoModel.getRegionName(),
                    regionInfoModel.getStatus(),
                    regionInfoModel.getCreateBy(),
                    regionInfoModel.getCreateTerminal(),
                    regionInfoModel.getCreateDate(),
                    regionInfoModel.getUpdateBy(),
                    regionInfoModel.getUpdateTerminal(),
                    regionInfoModel.getUpdateDate());
        }

        return regionInfoModel;
    }

    public String generateNextRegionCode() {

        String sql = "SELECT MAX(REGION_CODE) FROM REGION_INFO WHERE REGION_CODE LIKE 'A%'";
        String lastCode = jdbcTemplate.queryForObject(sql, String.class);
        int nextCodeNumber = 1;
        if (lastCode != null && lastCode.length() > 1) {
            String numericPart = lastCode.substring(1);
            try {
                int lastCodeNumber = Integer.parseInt(numericPart);
                nextCodeNumber = lastCodeNumber + 1;
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid format for REGION_CODE in database: " + lastCode);
            }
        }
        return String.format("R%02d", nextCodeNumber);
    }

    public List<RegionInfoModel> findAll() {
        String sql = "SELECT * FROM REGION_INFO";
        return jdbcTemplate.query(sql, new RegionInfoRepository.RegionInfoRowMapper());
    }

    private static class RegionInfoRowMapper implements RowMapper<RegionInfoModel> {
        @Override
        public RegionInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            RegionInfoModel model = new RegionInfoModel();
            model.setId(rs.getLong("ID"));
            model.setRegionCode(rs.getString("REGION_CODE"));
            model.setRegionName(rs.getString("REGION_NAME"));
            model.setStatus(rs.getString("STATUS"));
//            areaInfoModel.setCreateBy(rs.getString("CREATE_BY"));
//            areaInfoModel.setCreateTerminal(rs.getString("CREATE_TERMINAL"));
//            areaInfoModel.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
//            areaInfoModel.setUpdateBy(rs.getString("UPDATE_BY"));
//            areaInfoModel.setUpdateTerminal(rs.getString("UPDATE_TERMINAL"));
//            areaInfoModel.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());
            return model;
        }
    }

    public List<RegionInfoModel> findByAnyData(RegionInfoModel criteria) {
        // Base SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM REGION_INFO WHERE 1=1");

        // List to hold parameter values
        List<Object> params = new ArrayList<>();

        // Build SQL query based on non-null fields in the criteria model
        if (criteria.getId() != null) {
            sql.append(" AND ID = ?");
            params.add(criteria.getId());
        }
        if (criteria.getRegionCode() != null) {
            sql.append(" AND REGION_CODE = ?");
            params.add(criteria.getRegionCode());
        }
        if (criteria.getRegionName() != null) {
            sql.append(" AND REGION_NAME LIKE ?");
            params.add("%" + criteria.getRegionName() + "%");
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
        return jdbcTemplate.query(sql.toString(), params.toArray(), new RegionInfoRepository.RegionInfoRowMapper());
    }

}

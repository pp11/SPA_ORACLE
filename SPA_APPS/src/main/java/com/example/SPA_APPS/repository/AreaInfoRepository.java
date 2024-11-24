package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.entity.DivisionInfo;
import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.DivisionInfoModel;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AreaInfoRepository {
    private final JdbcTemplate jdbcTemplate;


//
//    @Autowired
//    public AreaInfoRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public AreaInfoModel saveOrUpdateAreaInfo(AreaInfoModel areaInfoModel) {
        // Check if the record exists
        String existsSql = "SELECT COUNT(*) FROM AREA_INFO WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{areaInfoModel.getId()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE AREA_INFO SET  AREA_NAME = ?, STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ID = ?";
            jdbcTemplate.update(updateSql,
                  //  areaInfoModel.getAreaCode(),
                    areaInfoModel.getAreaName(),
                    areaInfoModel.getStatus(),
                    areaInfoModel.getUpdateBy(),
                    areaInfoModel.getUpdateTerminal(),
                    areaInfoModel.getUpdateDate(),
                    areaInfoModel.getId());
        } else {
            // If the record doesn't exist, insert it with a new ID from the sequence


            String insertSql = "INSERT INTO AREA_INFO (ID, AREA_CODE, AREA_NAME, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (AREA_INFO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String generatedCode = generateNextAreaCode();
            areaInfoModel.setAreaCode(generatedCode);
            jdbcTemplate.update(insertSql,
                    generatedCode,
                    areaInfoModel.getAreaName(),
                    areaInfoModel.getStatus(),
                    areaInfoModel.getCreateBy(),
                    areaInfoModel.getCreateTerminal(),
                    areaInfoModel.getCreateDate(),
                    areaInfoModel.getUpdateBy(),
                    areaInfoModel.getUpdateTerminal(),
                    areaInfoModel.getUpdateDate());
            String getGeneratedIdSql = "SELECT AREA_INFO_SEQ.currval FROM DUAL";
            Long generatedId = jdbcTemplate.queryForObject(getGeneratedIdSql, Long.class);

            areaInfoModel.setId(generatedId);
        }

        return areaInfoModel;
    }
    public String generateNextAreaCode() {

        String sql = "SELECT MAX(AREA_CODE) FROM AREA_INFO WHERE AREA_CODE LIKE 'A%'";

        String lastCode = jdbcTemplate.queryForObject(sql, String.class);

        int nextCodeNumber = 1;
        if (lastCode != null && lastCode.length() > 1) {
            // Extract the numeric part from lastCode, e.g., "A01" -> 1
            String numericPart = lastCode.substring(1);
            try {
                int lastCodeNumber = Integer.parseInt(numericPart);
                nextCodeNumber = lastCodeNumber + 1;
            } catch (NumberFormatException e) {

                throw new IllegalStateException("Invalid format for AREA_CODE in database: " + lastCode);
            }
        }

        // Format the code to always have two digits, e.g., "A01", "A02"
        return String.format("A%02d", nextCodeNumber);
    }


    public List<AreaInfoModel> findAll() {
        String sql = "SELECT * FROM AREA_INFO";
        return jdbcTemplate.query(sql, new AreaInfoRowMapper());
    }

    private static class AreaInfoRowMapper implements RowMapper<AreaInfoModel> {
        @Override
        public AreaInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AreaInfoModel areaInfoModel = new AreaInfoModel();
            areaInfoModel.setId(rs.getLong("ID"));
            areaInfoModel.setAreaCode(rs.getString("AREA_CODE"));
            areaInfoModel.setAreaName(rs.getString("AREA_NAME"));
            areaInfoModel.setStatus(rs.getString("STATUS"));
            areaInfoModel.setCreateBy(rs.getString("CREATE_BY"));
            areaInfoModel.setCreateTerminal(rs.getString("CREATE_TERMINAL"));
//            areaInfoModel.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            areaInfoModel.setUpdateBy(rs.getString("UPDATE_BY"));
            areaInfoModel.setUpdateTerminal(rs.getString("UPDATE_TERMINAL"));
//            areaInfoModel.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());

            Timestamp createDateTimestamp = rs.getTimestamp("CREATE_DATE");
            if (createDateTimestamp != null) {
                areaInfoModel.setCreateDate(createDateTimestamp.toLocalDateTime());
            }

            Timestamp updateDateTimestamp = rs.getTimestamp("UPDATE_DATE");
            if (updateDateTimestamp != null) {
                areaInfoModel.setUpdateDate(updateDateTimestamp.toLocalDateTime());
            }

            return areaInfoModel;
        }
    }


    public List<AreaInfoModel> findByAnyData(AreaInfoModel criteria) {
        // Base SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM AREA_INFO WHERE 1=1");

        // List to hold parameter values
        List<Object> params = new ArrayList<>();

        // Build SQL query based on non-null fields in the criteria model
        if (criteria.getId() != null) {
            sql.append(" AND ID = ?");
            params.add(criteria.getId());
        }
        if (criteria.getAreaCode() != null) {
            sql.append(" AND AREA_CODE = ?");
            params.add(criteria.getAreaCode());
        }
        if (criteria.getAreaName() != null) {
            sql.append(" AND AREA_NAME LIKE ?");
            params.add("%" + criteria.getAreaName() + "%");
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
        return jdbcTemplate.query(sql.toString(), params.toArray(), new AreaInfoRowMapper());
    }
}

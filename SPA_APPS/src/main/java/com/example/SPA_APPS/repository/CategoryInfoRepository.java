package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.model.CategoryInfoModel;
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
public class CategoryInfoRepository {
    private final JdbcTemplate jdbcTemplate;

    public String generateCode() {

        String prefix = "CA";
        String sql = "SELECT MAX(CATEGORY_CODE) FROM CATEGORY_INFO WHERE CATEGORY_CODE LIKE ?";
        String lastCode = jdbcTemplate.queryForObject(sql, new Object[]{prefix + "%"}, String.class);

        int nextNumber = 1;
        if (lastCode != null && lastCode.startsWith(prefix)) {

            String numericPart = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(numericPart) + 1;
        }
        return String.format("%s%03d", prefix, nextNumber);
    }


    public CategoryInfoModel saveOrUpdate(CategoryInfoModel categoryInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM CATEGORY_INFO WHERE CATEGORY_CODE = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{categoryInfoModel.getCategoryCode()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE CATEGORY_INFO SET  CATEGORY_NAME = ?,BRAND_CODE=?,  STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE CATEGORY_CODE = ?";
            jdbcTemplate.update(updateSql,
                    categoryInfoModel.getCategoryName(),
                    categoryInfoModel.getBrandCode(),
                    categoryInfoModel.getStatus(),
                    categoryInfoModel.getUpdateBy(),
                    categoryInfoModel.getUpdateTerminal(),
                    categoryInfoModel.getUpdateDate(),
                    categoryInfoModel.getCategoryCode());
        } else {
            String generatedCode = generateCode();
            categoryInfoModel.setCategoryCode(generatedCode);

            String insertSql = "INSERT INTO CATEGORY_INFO (CATEGORY_CODE, CATEGORY_NAME, BRAND_CODE, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generatedCode,
                    categoryInfoModel.getCategoryName(),
                    categoryInfoModel.getBrandCode(),
                    categoryInfoModel.getStatus(),
                    categoryInfoModel.getCreateBy(),
                    categoryInfoModel.getCreateTerminal(),
                    categoryInfoModel.getCreateDate(),
                    categoryInfoModel.getUpdateBy(),
                    categoryInfoModel.getUpdateTerminal(),
                    categoryInfoModel.getUpdateDate());
        }

        return categoryInfoModel;
    }


    private static class CategoryInfoRowMapper implements RowMapper<CategoryInfoModel> {
        @Override
        public CategoryInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoryInfoModel model = new CategoryInfoModel();
            model.setCategoryCode(rs.getString("CATEGORY_CODE"));
            model.setCategoryName(rs.getString("CATEGORY_NAME"));
            model.setBrandCode(rs.getString("BRAND_CODE"));
            model.setStatus(rs.getString("STATUS"));
            model.setCreateBy(rs.getString("CREATE_BY"));
            model.setCreateTerminal(rs.getString("CREATE_TERMINAL"));
            model.setUpdateBy(rs.getString("UPDATE_BY"));
            model.setUpdateTerminal(rs.getString("UPDATE_TERMINAL"));
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
    public List<CategoryInfoModel> findAll() {
        String sql = "SELECT * FROM CATEGORY_INFO";
        return jdbcTemplate.query(sql, new CategoryInfoRepository.CategoryInfoRowMapper());
    }

    public List<CategoryInfoModel> findByAnyData(CategoryInfoModel criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM CATEGORY_INFO WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getCategoryCode() != null) {
            sql.append(" AND CATEGORY_CODE = ?");
            params.add(criteria.getCategoryCode());
        }
        if (criteria.getCategoryName() != null) {
            sql.append(" AND CATEGORY_NAME LIKE ?");
            params.add("%" + criteria.getCategoryName() + "%");
        }
        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }
        if (criteria.getBrandCode() != null) {
            sql.append(" AND BRAND_CODE = ?");
            params.add(criteria.getBrandCode());
        }
        return jdbcTemplate.query(sql.toString(), params.toArray(), new CategoryInfoRepository.CategoryInfoRowMapper());
    }

}

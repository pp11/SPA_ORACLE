package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.BaseProductModel;
import com.example.SPA_APPS.model.BrandInfoModel;
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
public class BaseProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public BaseProductModel saveOrUpdate(BaseProductModel baseProductModel) {

        String existsSql = "SELECT COUNT(*) FROM BASE_PRODUCT_INFO WHERE BASE_PRODUCT_CODE = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{baseProductModel.getBaseProductCode()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE BASE_PRODUCT_INFO SET  BASE_PRODUCT_NAME = ?,  STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE BASE_PRODUCT_CODE = ?";
            jdbcTemplate.update(updateSql,
                    baseProductModel.getBaseProductName(),
                    baseProductModel.getStatus(),
                    baseProductModel.getUpdateBy(),
                    baseProductModel.getUpdateTerminal(),
                    baseProductModel.getUpdateDate(),
                    baseProductModel.getBaseProductCode());
        } else {
            String insertSql = "INSERT INTO BASE_PRODUCT_INFO (BASE_PRODUCT_CODE, BASE_PRODUCT_NAME,  STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generateCode(),
                    baseProductModel.getBaseProductName(),
                    baseProductModel.getStatus(),
                    baseProductModel.getCreateBy(),
                    baseProductModel.getCreateTerminal(),
                    baseProductModel.getCreateDate(),
                    baseProductModel.getUpdateBy(),
                    baseProductModel.getUpdateTerminal(),
                    baseProductModel.getUpdateDate());
        }

        return baseProductModel;
    }

    public String generateCode() {

        String prefix = "BP";
        String sql = "SELECT MAX(BASE_PRODUCT_CODE) FROM BASE_PRODUCT_INFO WHERE BASE_PRODUCT_CODE LIKE ?";
        String lastCode = jdbcTemplate.queryForObject(sql, new Object[]{prefix + "%"}, String.class);

        int nextNumber = 1;
        if (lastCode != null && lastCode.startsWith(prefix)) {

            String numericPart = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(numericPart) + 1;
        }
        return String.format("%s%02d", prefix, nextNumber);
    }

    public List<BaseProductModel> findAllBaseProduct() {
        String sql = "SELECT * FROM BASE_PRODUCT_INFO";
        return jdbcTemplate.query(sql, new BaseProductRepository.BaseProductRowMapper());
    }
    private static class BaseProductRowMapper implements RowMapper<BaseProductModel> {
        @Override
        public BaseProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            BaseProductModel model = new BaseProductModel();

            model.setBaseProductCode(rs.getString("BASE_PRODUCT_CODE"));
            model.setBaseProductName(rs.getString("BASE_PRODUCT_NAME"));
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

    public List<BaseProductModel> findByAnyData(BaseProductModel criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM BASE_PRODUCT_INFO WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getBaseProductCode() != null) {
            sql.append(" AND BASE_PRODUCT_CODE = ?");
            params.add(criteria.getBaseProductCode());
        }
        if (criteria.getBaseProductName() != null) {
            sql.append(" AND BASE_PRODUCT_NAME LIKE ?");
            params.add("%" + criteria.getBaseProductName() + "%");
        }
        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BaseProductRepository.BaseProductRowMapper());
    }
}

package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.model.ProductInfoModel;
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
public class BrandRepository {
    private final JdbcTemplate jdbcTemplate;

    public BrandInfoModel saveOrUpdate(BrandInfoModel brandInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM BRAND_INFO WHERE BRAND_CODE = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{brandInfoModel.getBrandCode()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE BRAND_INFO SET  BRAND_NAME = ?, BRAND_TYPE=?,  STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE BRAND_CODE = ?";
            jdbcTemplate.update(updateSql,
                    brandInfoModel.getBrandName(),
                    brandInfoModel.getBrandType(),
                    brandInfoModel.getStatus(),
                    brandInfoModel.getUpdateBy(),
                    brandInfoModel.getUpdateTerminal(),
                    brandInfoModel.getUpdateDate(),
                    brandInfoModel.getBrandCode());
        } else {
            String insertSql = "INSERT INTO BRAND_INFO (BRAND_CODE, BRAND_NAME, BRAND_TYPE, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String generatedCode = generateCode();
            brandInfoModel.setBrandCode(generatedCode);
            jdbcTemplate.update(insertSql,
                    generatedCode,
                    brandInfoModel.getBrandName(),
                    brandInfoModel.getBrandType(),
                    brandInfoModel.getStatus(),
                    brandInfoModel.getCreateBy(),
                    brandInfoModel.getCreateTerminal(),
                    brandInfoModel.getCreateDate(),
                    brandInfoModel.getUpdateBy(),
                    brandInfoModel.getUpdateTerminal(),
                    brandInfoModel.getUpdateDate());
        }

        return brandInfoModel;
    }

    public String generateCode() {

        String prefix = "BN";
        String sql = "SELECT MAX(BRAND_CODE) FROM BRAND_INFO WHERE BRAND_CODE LIKE ?";
        String lastCode = jdbcTemplate.queryForObject(sql, new Object[]{prefix + "%"}, String.class);

        int nextNumber = 1;
        if (lastCode != null && lastCode.startsWith(prefix)) {

            String numericPart = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(numericPart) + 1;
        }
        return String.format("%s%03d", prefix, nextNumber);
    }

    private static class BrandInfoRowMapper implements RowMapper<BrandInfoModel> {
        @Override
        public BrandInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            BrandInfoModel model = new BrandInfoModel();

            model.setBrandCode(rs.getString("BRAND_CODE"));
            model.setBrandName(rs.getString("BRAND_NAME"));
            model.setBrandType(rs.getString("BRAND_TYPE"));
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
    public List<BrandInfoModel> findAll() {
        String sql = "SELECT * FROM BRAND_INFO";
        return jdbcTemplate.query(sql, new BrandRepository.BrandInfoRowMapper());
    }

    public List<BrandInfoModel> findByAnyData(BrandInfoModel criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM BRAND_INFO WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getBrandCode() != null) {
            sql.append(" AND BRAND_CODE = ?");
            params.add(criteria.getBrandCode());
        }
        if (criteria.getBrandName() != null) {
            sql.append(" AND BRAND_NAME LIKE ?");
            params.add("%" + criteria.getBrandName() + "%");
        }
        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }
        if (criteria.getBrandType() != null) {
            sql.append(" AND BRAND_TYPE = ?");
            params.add(criteria.getBrandType());
        }
        return jdbcTemplate.query(sql.toString(), params.toArray(), new BrandRepository.BrandInfoRowMapper());
    }


}

package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.ProductInfoModel;
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
public class ProductInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductInfoModel saveOrUpdate(ProductInfoModel productInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM PRODUCT_INFO WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{productInfoModel.getId()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE PRODUCT_INFO SET  PRODUCT_NAME = ?, PRODUCT_NAME_BN=?, PACK_SIZE=?, GROUP_CODE=?, BRAND_CODE=?, " +
                    "CATEGORY_CODE=?, BASE_PRODUCT_CODE=?, SHIPPER_QTY=?, " +
                    "STATUS = ?,PRODUCT_VERSION= ?,CP_FLAG=?, PROD_UNIT_MST_SLNO=?, PACK_SIZE_GRAM=? , " +
                    " UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ID = ?";
            jdbcTemplate.update(updateSql,
                    productInfoModel.getProductName(),
                    productInfoModel.getProductNameBengali(),
                    productInfoModel.getPackSize(),
                    productInfoModel.getGroupCode(),
                    productInfoModel.getBrandCode(),
                    productInfoModel.getCategoryCode(),
                    productInfoModel.getBaseProductCode(),
                    productInfoModel.getShipperQty(),
                    productInfoModel.getStatus(),
                    productInfoModel.getVersionNo(),
                    productInfoModel.getCpFlag(),
                    productInfoModel.getProdUnitSlno(),
                    productInfoModel.getPackSizeGram(),
                    productInfoModel.getUpdateBy(),
                    productInfoModel.getUpdateTerminal(),
                    productInfoModel.getUpdateDate(),
                    productInfoModel.getId());
        } else {
            String insertSql = "INSERT INTO PRODUCT_INFO (ID, PRODUCT_CODE, PRODUCT_NAME, PRODUCT_NAME_BN, PACK_SIZE, GROUP_CODE, " +
                    "BRAND_CODE, CATEGORY_CODE, BASE_PRODUCT_CODE, SHIPPER_QTY, STATUS, PRODUCT_VERSION, CP_FLAG, PROD_UNIT_MST_SLNO, " +
                    "PACK_SIZE_GRAM, CREATE_BY, CREATE_DATE, CREATE_TERMINAL, UPDATE_BY, UPDATE_DATE, UPDATE_TERMINAL) " +
                    "VALUES (PRODUCT_INFO_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    productInfoModel.getProductCode(),
                    productInfoModel.getProductName(),
                    productInfoModel.getProductNameBengali(),
                    productInfoModel.getPackSize(),
                    productInfoModel.getGroupCode(),
                    productInfoModel.getBrandCode(),
                    productInfoModel.getCategoryCode(),
                    productInfoModel.getBaseProductCode(),
                    productInfoModel.getShipperQty(),
                    productInfoModel.getStatus(),
                    productInfoModel.getVersionNo(),
                    productInfoModel.getCpFlag(),
                    productInfoModel.getProdUnitSlno(),
                    productInfoModel.getPackSizeGram(),
                    productInfoModel.getCreateBy(),
                    productInfoModel.getCreateDate(),
                    productInfoModel.getCreateTerminal(),
                    productInfoModel.getUpdateBy(),
                    productInfoModel.getUpdateDate(),
                    productInfoModel.getUpdateTerminal());
        }
        return productInfoModel;
    }

    public List<ProductInfoModel> findAll() {
        String sql = "SELECT * FROM PRODUCT_INFO";
        return jdbcTemplate.query(sql, new ProductInfoRepository.ProductInfoRowMapper());
    }

    private static class ProductInfoRowMapper implements RowMapper<ProductInfoModel> {
        @Override
        public ProductInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductInfoModel model = new ProductInfoModel();
            model.setId(rs.getLong("ID"));
            model.setProductCode(rs.getString("PRODUCT_CODE"));
            model.setProductName(rs.getString("PRODUCT_NAME"));
            model.setPackSize(rs.getString("PACK_SIZE"));
            model.setPackSizeGram(rs.getString("PACK_SIZE_GRAM"));
            model.setGroupCode(rs.getString("GROUP_CODE"));
            model.setBrandCode(rs.getString("BRAND_CODE"));
            model.setBaseProductCode(rs.getString("BASE_PRODUCT_CODE"));
            model.setShipperQty(rs.getInt("SHIPPER_QTY"));
            model.setStatus(rs.getString("STATUS"));
            model.setVersionNo(rs.getInt("PRODUCT_VERSION"));
            model.setCpFlag(rs.getString("CP_FLAG"));
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

    public List<ProductInfoModel> findByAnyData(ProductInfoModel criteria) {
        // Base SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCT_INFO WHERE 1=1");

        // List to hold parameter values
        List<Object> params = new ArrayList<>();

        // Build SQL query based on non-null fields in the criteria model
        if (criteria.getId() != null) {
            sql.append(" AND ID = ?");
            params.add(criteria.getId());
        }
        if (criteria.getProductCode() != null) {
            sql.append(" AND PRODUCT_CODE = ?");
            params.add(criteria.getProductCode());
        }
        if (criteria.getProductName() != null) {
            sql.append(" AND PRODUCT_NAME LIKE ?");
            params.add("%" + criteria.getProductName() + "%");
        }
        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }
        if (criteria.getPackSize() != null) {
            sql.append(" AND PACK_SIZE = ?");
            params.add(criteria.getPackSize());
        }
        if (criteria.getBrandCode() != null) {
            sql.append(" AND BRAND_CODE = ?");
            params.add(criteria.getBrandCode());
        }
        if (criteria.getGroupCode() != null) {
            sql.append(" AND GROUP_CODE = ?");
            params.add(criteria.getGroupCode());
        }
        if (criteria.getCategoryCode() != null) {
            sql.append(" AND CATEGORY_CODE = ?");
            params.add(criteria.getCategoryCode());
        }
        if (criteria.getBaseProductCode() != null) {
            sql.append(" AND BASE_PRODUCT_CODE = ?");
            params.add(criteria.getBaseProductCode());
        }


        // Execute the query with the constructed SQL and parameters
        return jdbcTemplate.query(sql.toString(), params.toArray(), new ProductInfoRepository.ProductInfoRowMapper());
    }

}

package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.ProductPriceInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductPriceInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductPriceInfoModel saveOrUpdate(ProductPriceInfoModel productPriceInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM PRODUCT_PRICE_INFO WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{productPriceInfoModel.getId()}, Integer.class);

        String existsDatePrice = "SELECT COUNT(*) FROM PRODUCT_PRICE_INFO WHERE PRODUCT_CODE =? AND  TO_DATE(PRICE_DATE)=?";
        Integer countPrice = jdbcTemplate.queryForObject(existsDatePrice, new Object[]{productPriceInfoModel.getProductCode(), productPriceInfoModel.getPriceDate()}, Integer.class);

        if(countPrice!=null && countPrice>0 ){
            throw new IllegalArgumentException("Invalid state: Price is declared already for this product");

        }
        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE PRODUCT_PRICE_INFO SET  UNIT_TP = ?, UNIT_VAT=?, MRP=?, EMPLOYEE_PRICE=?, SPECIAL_PRICE=?, " +
                    " UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ID = ?";
            jdbcTemplate.update(updateSql,
                    productPriceInfoModel.getUnitTp(),
                    productPriceInfoModel.getUnitVat(),
                    productPriceInfoModel.getEmployeePrice(),
                    productPriceInfoModel.getSpecialPrice(),
                    productPriceInfoModel.getMrp(),
                    productPriceInfoModel.getUpdateBy(),
                    productPriceInfoModel.getUpdateTerminal(),
                    productPriceInfoModel.getUpdateDate(),
                    productPriceInfoModel.getId());
        } else {

            String insertSql = "INSERT INTO PRODUCT_PRICE_INFO (ID, PRICE_DATE, PRODUCT_CODE, UNIT_TP, UNIT_VAT, MRP, EMPLOYEE_PRICE, " +
                    "SPECIAL_PRICE, CREATE_BY, CREATE_DATE, CREATE_TERMINAL, UPDATE_BY, UPDATE_DATE, UPDATE_TERMINAL) " +
                    "VALUES (PRODUCT_PRICE_INFO_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(insertSql,
                    productPriceInfoModel.getPriceDate(),
                    productPriceInfoModel.getProductCode(),
                    productPriceInfoModel.getUnitTp(),
                    productPriceInfoModel.getUnitVat(),
                    productPriceInfoModel.getMrp(),
                    productPriceInfoModel.getEmployeePrice(),
                    productPriceInfoModel.getSpecialPrice(),
                    productPriceInfoModel.getCreateBy(),
                    productPriceInfoModel.getCreateDate(),
                    productPriceInfoModel.getCreateTerminal(),
                    productPriceInfoModel.getUpdateBy(),
                    productPriceInfoModel.getUpdateDate(),
                    productPriceInfoModel.getUpdateTerminal());

            String getGeneratedIdSql = "SELECT PRODUCT_PRICE_INFO_SEQ.currval FROM DUAL";
            Long generatedId = jdbcTemplate.queryForObject(getGeneratedIdSql, Long.class);
            productPriceInfoModel.setId(generatedId);
        }
        return productPriceInfoModel;
    }

    public List<ProductPriceInfoModel> findAllPrice() {
        String sql = "SELECT * FROM PRODUCT_PRICE_INFO order by  id desc";
        return jdbcTemplate.query(sql, new ProductPriceInfoRepository.ProductPriceInfoRowMapper());
    }
    private static class ProductPriceInfoRowMapper implements RowMapper<ProductPriceInfoModel> {
        @Override
        public ProductPriceInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductPriceInfoModel model = new ProductPriceInfoModel();
            model.setId(rs.getLong("ID"));
            model.setProductCode(rs.getString("PRODUCT_CODE"));
//            model.setProductName(rs.getString("PRODUCT_NAME"));
//            model.setPackSize(rs.getString("PACK_SIZE"));
            model.setPriceDate(rs.getDate("PRICE_DATE").toLocalDate());
            model.setUnitTp(rs.getDouble("UNIT_TP"));
            model.setUnitVat(rs.getDouble("UNIT_VAT"));
            model.setSpecialPrice(rs.getDouble("SPECIAL_PRICE"));
            model.setEmployeePrice(rs.getDouble("EMPLOYEE_PRICE"));
            model.setMrp(rs.getDouble("MRP"));
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
}

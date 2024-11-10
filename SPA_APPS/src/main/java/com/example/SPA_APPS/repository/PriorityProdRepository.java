package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.PriorityProdDtlModel;
import com.example.SPA_APPS.model.PriorityProdMstModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PriorityProdRepository {
    private final JdbcTemplate jdbcTemplate;

    public PriorityProdRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PriorityProdMstModel savePriorityProdMst(PriorityProdMstModel mstModel) {
        String sql = "INSERT INTO PRIORITY_PROD_MST (MST_ID, EFFECT_START_DATE, EFFECT_END_DATE, REMARKS,UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                "VALUES (PRIO_PROD_MST_SEQ.NEXTVAL, ?, ?, ?,?, ?, ?)";

        jdbcTemplate.update(sql,
                mstModel.getEffectStartDate(),
                mstModel.getEffectEndDate(),
                mstModel.getRemarks(),
                mstModel.getCreateBy(),
                mstModel.getCreateTerminal(),
                mstModel.getCreateDate());

        String fetchMstIdSql = "SELECT PRIO_PROD_MST_SEQ.CURRVAL FROM DUAL";
        Long mstSlno = jdbcTemplate.queryForObject(fetchMstIdSql, Long.class);
        mstModel.setMstId(mstSlno);
        return mstModel;
    }


    public PriorityProdDtlModel savePriorityProdDtl(PriorityProdDtlModel dtlModel) {
        String sql = "INSERT INTO PRIORITY_PROD_DTL (DTL_ID, MST_ID, PRODUCT_CODE, STATUS, REMARKS,CREATE_BY, CREATE_TERMINAL, CREATE_DATE) " +
                "VALUES (PRIO_PROD_DTL_SEQ.NEXTVAL, ?, ?, ?, ?,?,?,?)";

        // Insert into detail table
        jdbcTemplate.update(sql,
                dtlModel.getMstId(),
                dtlModel.getProductCode(),
                dtlModel.getStatus(),
                dtlModel.getRemarks(),
                dtlModel.getCreateBy(),
                dtlModel.getCreateTerminal(),
                dtlModel.getCreateDate());

        return dtlModel; // Return the saved detail model
    }
/*
    public PriorityProdMstModel savePriorityProdMst(PriorityProdMstModel mstModel) {
        // Get next value from the sequence for PRIO_PRO_MST_SLNO
        String sql = "SELECT PRIO_PROD_MST_SEQ.NEXTVAL FROM DUAL";
        Long mstSlno = jdbcTemplate.queryForObject(sql, Long.class);

        // Insert into PRIORITY_PROD_MST table with the generated mstSlno
        String insertSql = "INSERT INTO PRIORITY_PROD_MST (MST_ID, EFFECT_START_DATE, EFFECT_END_DATE, REMARKS) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, mstSlno, mstModel.getEffectStartDate(), mstModel.getEffectEndDate(), mstModel.getRemarks());

        // Set the generated mstSlno in the model and return it
        mstModel.setMstId(mstSlno);
        return mstModel;
    }


    public PriorityProdDtlModel savePriorityProdDtl(PriorityProdDtlModel dtlModel) {
        String dtlgenerateSql = "SELECT PRIO_PROD_DTL_SEQ.NEXTVAL FROM DUAL";
        Long dtlSlno = jdbcTemplate.queryForObject(dtlgenerateSql, Long.class);

        String sql = "INSERT INTO PRIORITY_PROD_DTL (DTL_ID, MST_ID, PRODUCT_CODE, STATUS, REMARKS) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, dtlSlno,dtlModel.getMstId(), dtlModel.getProductCode(), dtlModel.getStatus(), dtlModel.getRemarks());
        return dtlModel;
    }


    public PriorityProdMstModel findPriorityProdMstById(Long mstId) {
        String sql = "SELECT * FROM PRIORITY_PROD_MST WHERE MST_ID = ?";
        return jdbcTemplate.queryForObject(sql, new PriorityProdMstRowMapper(), mstId);
    }


    public List<PriorityProdDtlModel> findPriorityProdDtlsByMstId(Long mstId) {
        String sql = "SELECT * FROM PRIORITY_PROD_DTL WHERE MST_ID = ?";
        return jdbcTemplate.query(sql, new PriorityProdDtlRowMapper(), mstId);
    }

    // RowMapper for PriorityProdMstModel
    private static class PriorityProdMstRowMapper implements RowMapper<PriorityProdMstModel> {
        @Override
        public PriorityProdMstModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            PriorityProdMstModel model = new PriorityProdMstModel();
            model.setMstId(rs.getLong("MST_ID"));
            model.setEffectStartDate(rs.getDate("EFFECT_START_DATE"));
            model.setEffectEndDate(rs.getDate("EFFECT_END_DATE"));
            model.setRemarks(rs.getString("REMARKS"));
            return model;
        }
    }

    // RowMapper for PriorityProdDtlModel
    private static class PriorityProdDtlRowMapper implements RowMapper<PriorityProdDtlModel> {
        @Override
        public PriorityProdDtlModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            PriorityProdDtlModel model = new PriorityProdDtlModel();
            model.setDtlId(rs.getLong("DTL_ID"));
            model.setMstId(rs.getLong("MST_ID"));
            model.setProductCode(rs.getString("PRODUCT_CODE"));
            model.setStatus(rs.getString("STATUS"));
            model.setRemarks(rs.getString("REMARKS"));
            return model;
        }
    }*/
}

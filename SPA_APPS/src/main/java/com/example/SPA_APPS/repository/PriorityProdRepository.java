package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.PriorityProdDtlModel;
import com.example.SPA_APPS.model.PriorityProdMstModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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



    public PriorityProdMstModel searchByMstId(Long mstId) {
        // Query to fetch master record based on mstId
        String masterSql = "SELECT * FROM PRIORITY_PROD_MST mst WHERE mst.MST_ID = ?";

        // Get the master record (assuming there is only one result for the mstId)
        PriorityProdMstModel mstModel = jdbcTemplate.queryForObject(masterSql, new Object[]{mstId}, new RowMapper<PriorityProdMstModel>() {
            @Override
            public PriorityProdMstModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                PriorityProdMstModel mst = new PriorityProdMstModel();
                mst.setMstId(rs.getLong("MST_ID"));
                mst.setEffectStartDate(rs.getDate("EFFECT_START_DATE"));
                mst.setEffectEndDate(rs.getDate("EFFECT_END_DATE"));
                mst.setRemarks(rs.getString("REMARKS"));
                return mst;
            }
        });

        // Query to fetch details for the given mstId
        String detailSql = "SELECT * FROM PRIORITY_PROD_DTL dtl WHERE dtl.MST_ID = ?";
        List<PriorityProdDtlModel> dtlModels = jdbcTemplate.query(detailSql, new Object[]{mstId}, new RowMapper<PriorityProdDtlModel>() {
            @Override
            public PriorityProdDtlModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                PriorityProdDtlModel dtl = new PriorityProdDtlModel();
                dtl.setDtlId(rs.getLong("DTL_ID"));
                dtl.setMstId(rs.getLong("MST_ID"));
                dtl.setProductCode(rs.getString("PRODUCT_CODE"));
                dtl.setStatus(rs.getString("STATUS"));
                dtl.setRemarks(rs.getString("REMARKS"));
                return dtl;
            }
        });

        // Set the details in the master model
        if (mstModel != null) {
            mstModel.setDetails(dtlModels); // Attach all found detail records to the master record
        }

        return mstModel;
    }

    public PriorityProdMstModel updatePriorityProdMst(PriorityProdMstModel mstModel) {
        try {
            // Update master record
            String sql = "UPDATE PRIORITY_PROD_MST SET " +
//                    "EFFECT_START_DATE = ?, " +
//                    "EFFECT_END_DATE = ?, " +
                    "REMARKS = ?, " +
                    "UPDATE_BY = ?, " +
                    "UPDATE_TERMINAL = ?, " +
                    "UPDATE_DATE = ? " +
                    "WHERE MST_ID = ?";

            jdbcTemplate.update(sql,
//                    mstModel.getEffectStartDate(),
//                    mstModel.getEffectEndDate(),
                    mstModel.getRemarks(),
                    mstModel.getUpdateBy(),
                    mstModel.getUpdateTerminal(),
                    mstModel.getUpdateDate(),
                    mstModel.getMstId());
            return mstModel;
        } catch (DataAccessException e) {

            throw new RuntimeException("Error updating PriorityProdMst", e);
        }
    }


    public PriorityProdDtlModel updatePriorityProdDtl(PriorityProdDtlModel dtlModel) {
        try {
            // Update detail record
            String sql = "UPDATE PRIORITY_PROD_DTL SET " +
//                    "PRODUCT_CODE = ?, " +
                    "STATUS = ?, " +
                    "REMARKS = ?, " +
                    "UPDATE_BY = ?, " +
                    "UPDATE_TERMINAL = ?, " +
                    "UPDATE_DATE = ? " +
                    "WHERE DTL_ID = ?";

            jdbcTemplate.update(sql,
                    //dtlModel.getProductCode(),
                    dtlModel.getStatus(),
                    dtlModel.getRemarks(),
                    dtlModel.getUpdateBy(),
                    dtlModel.getUpdateTerminal(),
                    dtlModel.getUpdateDate(),
                    dtlModel.getDtlId());

            return dtlModel;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error updating PriorityProdDtl", e);
        }
    }

}

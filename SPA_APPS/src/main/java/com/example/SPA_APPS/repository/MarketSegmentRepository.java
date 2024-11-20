package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.CategoryInfoModel;
import com.example.SPA_APPS.model.MarketSegmentModel;
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
public class MarketSegmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public String generateCode() {

        String prefix = "MS";
        String sql = "SELECT MAX(MARKET_SEGMENT_CODE) FROM MARKET_SEGMENT_INFO WHERE MARKET_SEGMENT_CODE LIKE ?";
        String lastCode = jdbcTemplate.queryForObject(sql, new Object[]{prefix + "%"}, String.class);

        int nextNumber = 1;
        if (lastCode != null && lastCode.startsWith(prefix)) {

            String numericPart = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(numericPart) + 1;
        }
        return String.format("%s%02d", prefix, nextNumber);
    }


    public MarketSegmentModel saveOrUpdate(MarketSegmentModel marketSegmentModel) {

        String existsSql = "SELECT COUNT(*) FROM MARKET_SEGMENT_INFO WHERE MARKET_SEGMENT_CODE = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{marketSegmentModel.getMarketSegmentCode()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE MARKET_SEGMENT_INFO SET  MARKET_SEGMENT_NAME = ?,  STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE MARKET_SEGMENT_CODE = ?";
            jdbcTemplate.update(updateSql,
                    marketSegmentModel.getMarketSegmentName(),
                    marketSegmentModel.getStatus(),
                    marketSegmentModel.getUpdateBy(),
                    marketSegmentModel.getUpdateTerminal(),
                    marketSegmentModel.getUpdateDate(),
                    marketSegmentModel.getMarketSegmentCode());
        } else {
            String generatedCode = generateCode();
            marketSegmentModel.setMarketSegmentCode(generatedCode);

            String insertSql = "INSERT INTO MARKET_SEGMENT_INFO (MARKET_SEGMENT_CODE, MARKET_SEGMENT_NAME,  STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES (?, ?,  ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generatedCode,
                    marketSegmentModel.getMarketSegmentName(),
                    marketSegmentModel.getStatus(),
                    marketSegmentModel.getCreateBy(),
                    marketSegmentModel.getCreateTerminal(),
                    marketSegmentModel.getCreateDate(),
                    marketSegmentModel.getUpdateBy(),
                    marketSegmentModel.getUpdateTerminal(),
                    marketSegmentModel.getUpdateDate());
        }

        return marketSegmentModel;
    }

    private static class MarketSegmentRowMapper implements RowMapper<MarketSegmentModel> {
        @Override
        public MarketSegmentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            MarketSegmentModel model = new MarketSegmentModel();
            model.setMarketSegmentCode(rs.getString("MARKET_SEGMENT_CODE"));
            model.setMarketSegmentName(rs.getString("MARKET_SEGMENT_NAME"));
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
    public List<MarketSegmentModel> findAll() {
        String sql = "SELECT * FROM MARKET_SEGMENT_INFO";
        return jdbcTemplate.query(sql, new MarketSegmentRepository.MarketSegmentRowMapper());
    }

    public List<MarketSegmentModel> findByAnyData(MarketSegmentModel criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM MARKET_SEGMENT_INFO WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getMarketSegmentCode() != null) {
            sql.append(" AND MARKET_SEGMENT_CODE = ?");
            params.add(criteria.getMarketSegmentCode());
        }
        if (criteria.getMarketSegmentName() != null) {
            sql.append(" AND MARKET_SEGMENT_NAME LIKE ?");
            params.add("%" + criteria.getMarketSegmentName() + "%");
        }
        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }
        return jdbcTemplate.query(sql.toString(), params.toArray(), new MarketSegmentRepository.MarketSegmentRowMapper());
    }
}

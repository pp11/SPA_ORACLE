package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.MarketInfoModel;
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
public class MarketInfoRepository {

    private final JdbcTemplate jdbcTemplate;
    public String generateCode() {

        String prefix = "M";
        String sql = "SELECT MAX(MARKET_CODE) FROM MARKET_INFO WHERE MARKET_CODE LIKE ?";
        String lastCode = jdbcTemplate.queryForObject(sql, new Object[]{prefix + "%"}, String.class);

        int nextNumber = 1;
        if (lastCode != null && lastCode.startsWith(prefix)) {

            String numericPart = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(numericPart) + 1;
        }
        return String.format("%s%03d", prefix, nextNumber);
    }


    public MarketInfoModel saveOrUpdate(MarketInfoModel marketInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM MARKET_INFO WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{marketInfoModel.getId()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE MARKET_INFO SET  MARKET_NAME = ?,  MARKET_ADDRESS=?, MARKET_SEGMENT_CODE=?, STATUS = ?, REMARKS=?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ID = ?";
            jdbcTemplate.update(updateSql,
                    marketInfoModel.getMarketName(),
                    marketInfoModel.getMarketAddress(),
                    marketInfoModel.getMarketSegmentCode(),
                    marketInfoModel.getStatus(),
                    marketInfoModel.getRemarks(),
                    marketInfoModel.getUpdateBy(),
                    marketInfoModel.getUpdateTerminal(),
                    marketInfoModel.getUpdateDate(),
                    marketInfoModel.getId());
        } else {
            String generatedCode = generateCode();
            marketInfoModel.setMarketCode(generatedCode);

            String insertSql = "INSERT INTO MARKET_INFO (ID, MARKET_CODE, MARKET_NAME, MARKET_ADDRESS, MARKET_SEGMENT_CODE, REMARKS, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES  (MARKET_INFO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generatedCode,
                    marketInfoModel.getMarketName(),
                    marketInfoModel.getMarketAddress(),
                    marketInfoModel.getMarketSegmentCode(),
                    marketInfoModel.getRemarks(),
                    marketInfoModel.getStatus(),
                    marketInfoModel.getCreateBy(),
                    marketInfoModel.getCreateTerminal(),
                    marketInfoModel.getCreateDate(),
                    marketInfoModel.getUpdateBy(),
                    marketInfoModel.getUpdateTerminal(),
                    marketInfoModel.getUpdateDate());

            String getGeneratedIdSql = "SELECT MARKET_INFO_SEQ.currval FROM DUAL";
            Long generatedId = jdbcTemplate.queryForObject(getGeneratedIdSql, Long.class);

            marketInfoModel.setId(generatedId);
        }

        return marketInfoModel;
    }

    private static class MarketRowMapper implements RowMapper<MarketInfoModel> {
        @Override
        public MarketInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            MarketInfoModel model = new MarketInfoModel();
            model.setMarketCode(rs.getString("MARKET_CODE"));
            model.setMarketName(rs.getString("MARKET_NAME"));
            model.setMarketSegmentCode(rs.getString("MARKET_SEGMENT_CODE"));
            model.setMarketAddress(rs.getString("MARKET_ADDRESS"));
            model.setStatus(rs.getString("STATUS"));
            model.setRemarks(rs.getString("REMARKS"));
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
    public List<MarketInfoModel> findAll() {
        String sql = "SELECT * FROM MARKET_INFO";
        return jdbcTemplate.query(sql, new MarketInfoRepository.MarketRowMapper());
    }

    public List<MarketInfoModel> findByAnyData(MarketInfoModel criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM MARKET_INFO WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getMarketCode() != null) {
            sql.append(" AND MARKET_CODE = ?");
            params.add(criteria.getMarketCode());
        }
        if (criteria.getMarketName() != null) {
            sql.append(" AND MARKET_NAME LIKE ?");
            params.add("%" + criteria.getMarketName() + "%");
        }
        if (criteria.getMarketSegmentCode() != null) {
            sql.append(" AND MARKET_SEGMENT_CODE LIKE ?");
            params.add("%" + criteria.getMarketSegmentCode() + "%");
        }

        if (criteria.getStatus() != null) {
            sql.append(" AND STATUS = ?");
            params.add(criteria.getStatus());
        }
        return jdbcTemplate.query(sql.toString(), params.toArray(), new MarketInfoRepository.MarketRowMapper());
    }

}

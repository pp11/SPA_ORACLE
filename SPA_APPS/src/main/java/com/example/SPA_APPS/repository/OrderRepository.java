package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.model.OrderRequestModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall fncProcessOrderCall;

    public OrderRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.fncProcessOrderCall = new SimpleJdbcCall(dataSource)
                .withSchemaName("SALES")
                .withFunctionName("FNC_PROCESS_ORDER")
                .declareParameters(
                        new org.springframework.jdbc.core.SqlParameter("P_REGNO", Types.NUMERIC),
                        new org.springframework.jdbc.core.SqlParameter("P_ORDER", Types.VARCHAR),
                        new org.springframework.jdbc.core.SqlParameter("P_TYPE", Types.VARCHAR),
                        new org.springframework.jdbc.core.SqlOutParameter("RETURN", Types.VARCHAR)
                );
    }

//    public String processOrder(int regNo, String order, String type) {
//        Map<String, Object> result = fncProcessOrderCall.execute(regNo, order, type);
//        return (String) result.get("RETURN");
//    }

    public String processOrder(OrderRequestModel orderRequest) {
        // Extract parameters from the OrderRequest object
        int regNo = orderRequest.getRegNo();
        String order = orderRequest.getOrder();
        String type = orderRequest.getType();

        // Call the stored function with the model's data
        Map<String, Object> result = fncProcessOrderCall.execute(regNo, order, type);
        return (String) result.get("RETURN");
    }
}

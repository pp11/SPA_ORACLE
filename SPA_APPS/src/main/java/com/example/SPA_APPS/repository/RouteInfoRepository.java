package com.example.SPA_APPS.repository;


import com.example.SPA_APPS.model.RouteInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RouteInfoRepository {
    private final JdbcTemplate jdbcTemplate;
    public String generateCode() {

        String prefix = "R";
        String sql = "SELECT MAX(ROUTE_CODE) FROM ROUTE_INFO WHERE ROUTE_CODE LIKE ?";
        String lastCode = jdbcTemplate.queryForObject(sql, new Object[]{prefix + "%"}, String.class);

        int nextNumber = 1;
        if (lastCode != null && lastCode.startsWith(prefix)) {

            String numericPart = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(numericPart) + 1;
        }
        return String.format("%s%02d", prefix, nextNumber);
    }


    public RouteInfoModel saveOrUpdate(RouteInfoModel routeInfoModel) {

        String existsSql = "SELECT COUNT(*) FROM ROUTE_INFO WHERE ROUTE_CODE = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, new Object[]{routeInfoModel.getRouteCode()}, Integer.class);

        if (count != null && count > 0) {
            // If the record exists, update it
            String updateSql = "UPDATE ROUTE_INFO SET  ROUTE_NAME = ?,  ROUTE_DESC=?, REMARKS=?, STATUS = ?, UPDATE_BY = ?, UPDATE_TERMINAL = ?, UPDATE_DATE = ? WHERE ROUTE_CODE = ? ";
            jdbcTemplate.update(updateSql,
                    routeInfoModel.getRouteName(),
                    routeInfoModel.getRouteDescription(),
                    routeInfoModel.getRemarks(),
                    routeInfoModel.getStatus(),
                    routeInfoModel.getUpdateBy(),
                    routeInfoModel.getUpdateTerminal(),
                    routeInfoModel.getUpdateDate(),
                    routeInfoModel.getRouteCode());
        } else {
            String generatedCode = generateCode();
            routeInfoModel.setRouteCode(generatedCode);

            String insertSql = "INSERT INTO ROUTE_INFO ( ROUTE_CODE, ROUTE_NAME, ROUTE_DESC, REMARKS, STATUS, CREATE_BY, CREATE_TERMINAL, CREATE_DATE, UPDATE_BY, UPDATE_TERMINAL, UPDATE_DATE) " +
                    "VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    generatedCode,
                    routeInfoModel.getRouteName(),
                    routeInfoModel.getRouteDescription(),
                    routeInfoModel.getRemarks(),
                    routeInfoModel.getStatus(),
                    routeInfoModel.getCreateBy(),
                    routeInfoModel.getCreateTerminal(),
                    routeInfoModel.getCreateDate(),
                    routeInfoModel.getUpdateBy(),
                    routeInfoModel.getUpdateTerminal(),
                    routeInfoModel.getUpdateDate());
        }

        return routeInfoModel;
    }
}

package com.example.SPA_APPS.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationMappingRepository {
    private final JdbcTemplate jdbcTemplate;



}

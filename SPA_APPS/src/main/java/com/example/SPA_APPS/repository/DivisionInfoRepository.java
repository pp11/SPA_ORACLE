package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.entity.DivisionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionInfoRepository extends JpaRepository<DivisionInfo, Long>, JpaSpecificationExecutor<DivisionInfo> {
    Optional<DivisionInfo> findByDivisionCode(String divisionCode);


}

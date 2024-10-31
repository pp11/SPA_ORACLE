package com.example.SPA_APPS.repository;

import com.example.SPA_APPS.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TargetRepository  extends JpaRepository<Target, Long>, JpaSpecificationExecutor<Target> {
    Optional<Target> findByMarketCodeAndProductCodeAndYearAndMonth(String marketCode, String productCode, String year, String month);

    Optional<Target> findTargetById(Long id);
    List<Target> findByYearAndMonth(String year, String month);


}

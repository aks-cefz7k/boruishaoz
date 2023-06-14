package com.opentc.overflowopt.repository;


import com.opentc.overflowopt.entity.Overflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OverflowRepository extends JpaRepository<Overflow, Long> {


    List<Overflow> findByDetectionid(Long detectionId);
}

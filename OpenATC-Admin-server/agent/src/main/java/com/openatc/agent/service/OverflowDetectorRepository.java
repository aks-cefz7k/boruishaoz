package com.openatc.agent.service;


import com.openatc.agent.model.OverflowDetector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OverflowDetectorRepository extends JpaRepository<OverflowDetector, Long> {


}
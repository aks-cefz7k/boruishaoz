package com.openatc.agent.service;


import com.openatc.agent.model.OverflowDetector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OverflowDetectorRepository extends JpaRepository<OverflowDetector, Long> {

    @Transactional
    @Modifying
    @Query("update OverflowDetector o set o.status=?2 where o.id=?1")
    void updateStatusById(Long id, String status);

}
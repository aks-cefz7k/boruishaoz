package com.openatc.agent.service;




import com.openatc.agent.model.Overflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OverflowRepository extends JpaRepository<Overflow, Long> {


    List<Overflow> findByPatternid(Long patternId);

    List<Overflow> findByIntersectionid(String agentId);

    @Transactional
    @Modifying
    @Query("update Overflow o set o.state=?2 where o.intersectionid=?1")
    void updateIsopenByIntersectionid(String intersectionid, int state);
}

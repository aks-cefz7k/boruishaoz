package com.openatc.agent.service;




import com.openatc.agent.model.Overflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OverflowRepository extends JpaRepository<Overflow, Long> {


    List<Overflow> findByPatternid(Long patternId);
}

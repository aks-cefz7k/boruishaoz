package com.openatc.agent.service;

import com.openatc.agent.model.TStat;
import com.openatc.agent.model.TStatKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TStatDao extends JpaRepository<TStat, TStatKey> {
    List<TStat> findByAgentid(String agentid);

    @Transactional
    @Modifying
    @Query(value = "delete from t_stat where agentid = ?1", nativeQuery=true)
    int deleteByAgentid(String agentid);
}

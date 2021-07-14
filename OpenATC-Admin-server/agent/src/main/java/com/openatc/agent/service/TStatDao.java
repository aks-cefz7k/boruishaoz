package com.openatc.agent.service;

import com.openatc.agent.model.TStat;
import com.openatc.agent.model.TStatKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TStatDao extends JpaRepository<TStat, TStatKey> {
        List<TStat> findByAgentid(String agentid);
}

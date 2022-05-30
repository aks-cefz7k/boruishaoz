package com.openatc.agent.service;

import com.openatc.agent.model.DeviceVedio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DeviceVedioDao extends JpaRepository<DeviceVedio, Long> {

    List<DeviceVedio> findAllByAgentid(String agentid);

    @Transactional
    Integer deleteByAgentid(String agentid);

    List<DeviceVedio> findByAgentidIn(List<String> videoList);

}

package com.openatc.agent.service;

import com.openatc.agent.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemConfigDao extends JpaRepository<SystemConfig, Long> {
    List<SystemConfig> findAllByModule(String Module);

    List<SystemConfig> findAllByModuleAndAndIsValid(String Module, Boolean isValid);
}

package com.openatc.agent.service;

import com.openatc.agent.model.VipRouteDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;

public interface VipRouteDeviceDao extends JpaRepository<VipRouteDevice, Integer> {
    @Transactional
    void deleteByViprouteid(int id);

    VipRouteDevice findByViprouteidAndAgentid(int vipagentid, String agentid);

    List<VipRouteDevice> findByViprouteid(int id);
}

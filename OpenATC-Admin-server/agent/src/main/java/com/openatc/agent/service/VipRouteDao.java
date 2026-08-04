package com.openatc.agent.service;

import com.openatc.agent.model.VipRoute;
import com.openatc.agent.model.VipRouteDeviceOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VipRouteDao extends JpaRepository<VipRoute, Integer> {
    VipRoute findByName(String name);

    VipRoute findById(int id);

    @Query(value = "select v.agentid, d.status,case (LOCALTIMESTAMP - d.lastTime)< '1 min' when 'true' then 'UP' else 'DOWN' END AS state from vip_route_device v join dev d on v.agentid = d.agentid where v.viprouteid = ?1",nativeQuery=true)
    List<VipRouteDeviceOnline> findVipRouteWithDevStateById(int id);

    @Query(value = "SELECT new VipRoute(r.id, r.name) From VipRoute r order by id")
    List<VipRoute> getSimpleInfoForVipRoute();
}

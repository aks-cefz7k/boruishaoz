package com.openatc.agent.service;

import com.google.gson.JsonObject;
import com.openatc.agent.model.VipRoute;
import com.openatc.agent.model.VipRouteDeviceDevState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VipRouteDao extends JpaRepository<VipRoute, Integer> {
    VipRoute findByName(String name);

    VipRoute findById(int id);

    @Query(value = "select d.status from vip_route_device v join dev d on v.agentid = d.agentid where v.viprouteid = ?1",nativeQuery=true)
    List<VipRouteDeviceDevState> findVipRouteWithDevStateById(int id);

    @Query(value = "SELECT new VipRoute(r.id, r.name) From VipRoute r order by id")
    List<VipRoute> getSimpleInfoForVipRoute();
}

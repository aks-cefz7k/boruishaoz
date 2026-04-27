package com.openatc.agent.service;

import com.openatc.agent.model.VipRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VipRouteDao extends JpaRepository<VipRoute, Integer> {
    VipRoute findByName(String name);

    VipRoute findById(int id);

    @Query(value = "SELECT new VipRoute(r.id, r.name) From VipRoute r order by id")
    List<VipRoute> getSimpleInfoForVipRoute();
}

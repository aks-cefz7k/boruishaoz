package com.openatc.agent.service;

import com.openatc.agent.model.Fault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FaultDao extends JpaRepository<Fault, Long> {
    @Query(value="select id from fault where agentid= ?1 and m_w_faultid = ?2",nativeQuery=true)
    Long selectByAgentidAndMwFaultID(String agentid, Long m_wFaultID);

    @Query(value="select id, agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type from fault where m_un_fault_renew_time > 0",nativeQuery=true)
    List<Fault>  selectHistoryFaults();

    @Query(value="select id, agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type from fault where m_un_fault_renew_time = 0",nativeQuery=true)
    List<Fault>  selectCurrentFaults();

    @Query(value="select id, agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type from fault where m_un_fault_renew_time = 0 and agentid=?1",nativeQuery=true)
    List<Fault>  selectCurrentFaults(String agentid);

    @Query(value="select id, agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type from fault where m_un_fault_renew_time > 0 and agentid=?1",nativeQuery=true)
    List<Fault>  selectHistoryFaults(String agentid);

}

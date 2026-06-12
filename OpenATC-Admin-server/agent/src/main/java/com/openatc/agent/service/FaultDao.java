package com.openatc.agent.service;

import com.openatc.agent.model.Fault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FaultDao extends JpaRepository<Fault, Long> {

    Page<Fault> findAll(Specification<Fault> specification, Pageable pageable);

    @Query(value = "select agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type,operator,enumerate,operation_time,model,event_type,delete_flag from fault where agentid = ?1 and m_w_faultid = ?2", nativeQuery = true)
    List<Fault> findByAgentidAndM_wFaultID(String agentid, Long m_wFaultID);

    @Query(value = "select agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type,operator,enumerate,operation_time,model,event_type,delete_flag from fault where m_un_fault_renew_time > 0 and delete_flag <> '1'", nativeQuery = true)
    List<Fault> selectHistoryFaults();

    @Query(value = "select agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type,operator,enumerate,operation_time,model,event_type,delete_flag from fault where delete_flag <> '1'", nativeQuery = true)
    Page<Fault> selectCurrentFaults(Specification<Fault> specification, Pageable pageRequest);

    @Query(value = "select agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type,operator,enumerate,operation_time,model,event_type,delete_flag from fault where m_un_fault_renew_time = 0 and agentid=?1 and delete_flag <> '1'", nativeQuery = true)
    List<Fault> selectCurrentFaults(String agentid);

    @Query(value = "select agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type,operator,enumerate,operation_time,model,event_type,delete_flag from fault where m_un_fault_renew_time > 0 and agentid=?1 and delete_flag <> '1'", nativeQuery = true)
    List<Fault> selectHistoryFaults(String agentid);

    @Query(value = "select agentid, m_w_faultid, m_by_fault_board_type,m_by_fault_desc_value,m_by_fault_level,m_un_fault_occur_time,m_un_fault_renew_time,m_w_fault_type,m_w_sub_fault_type,operator,enumerate,operation_time,model,event_type,delete_flag from fault where delete_flag <> '1'", nativeQuery = true)
    Page<Fault> findAllFault(Specification<Fault> specification, Pageable pageRequest);

    @Transactional
    @Modifying
    @Query(value = "update fault set delete_flag = '1' where m_w_faultid = ?1 and agentid = ?2", nativeQuery = true)
    void deleteByMwFaultIdAndAgentId(Long faultId, String agentId);

    @Transactional
    @Modifying
    @Query(value = "update fault set enumerate = ?1, operator = ?2, operation_time = ?3  where m_w_faultid = ?4 and agentid = ?5", nativeQuery = true)
    void updateFault(String enumerate, String operator, Long operationTime, Long faultId, String agentId);

}

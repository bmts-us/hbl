/**
 * TicketRepository.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao.repositories;


import com.ht.offline.borlette.models.Ticket;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    //Get tickets
    @Query("SELECT t FROM Ticket t WHERE t.lotterySchedule.lotteryScheduleId = ?1 AND t.date= ?2")
    List<Ticket> findAll(long lotteryScheduleId, Date date);    
    
    //Get tickets of an agent
    @Query("SELECT t FROM Ticket t WHERE t.agent.agentId = ?1 AND t.lotterySchedule.lotteryScheduleId = ?2 AND t.date= ?3")
    List<Ticket> findAll(long agentId, long lotteryScheduleId, Date date);       
}


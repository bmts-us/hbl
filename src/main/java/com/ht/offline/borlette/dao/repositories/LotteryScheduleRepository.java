/**
 * LotteryScheduleRepository.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao.repositories;

import com.ht.offline.borlette.models.LotterySchedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryScheduleRepository extends JpaRepository<LotterySchedule, Long>{
    // Find lottery schedules
    @Query("SELECT ls FROM LotterySchedule ls")
    List<LotterySchedule> findAll();	
}


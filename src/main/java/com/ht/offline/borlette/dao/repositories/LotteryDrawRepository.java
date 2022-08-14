/**
 * LotteryDrawRepository.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao.repositories;

import com.ht.offline.borlette.models.LotteryDraw;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryDrawRepository extends JpaRepository<LotteryDraw, Long>{
    // Get LotteryDraw
    @Query("SELECT ld FROM LotteryDraw ld WHERE ld.lotterySchedule.lotteryScheduleId = ?1 AND ld.date = ?2")	
    LotteryDraw loadLotteryDraw(long lotteryScheduleId, Date date);    
}


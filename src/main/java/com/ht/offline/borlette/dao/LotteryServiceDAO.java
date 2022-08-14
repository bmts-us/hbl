/**
 * LotteryServiceDAO.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import java.util.Date;
import java.util.List;

import com.ht.offline.borlette.models.LotteryDraw;
import com.ht.offline.borlette.models.Ticket;

public interface LotteryServiceDAO {
   LotteryDraw persistLotteryDraw(LotteryDraw lotteryDraw, List<Ticket> ticketsWon);
   LotteryDraw loadLotteryDraw(long lotteryScheduleId, Date date);
   void reverseLotteryDraw(long lotteryScheduleId, Date date);
}
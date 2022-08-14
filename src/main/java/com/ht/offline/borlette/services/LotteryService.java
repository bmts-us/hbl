/**
 * LotteryService.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dtos.LotteryDrawDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface LotteryService extends Serializable {
  Map<String, Object> persistLotteryDraw(LotteryDrawDTO lotteryDraw);
  LotteryDrawDTO loadLotteryDraw(long lotteryScheduleId, Date date);
  boolean reverseLotteryDraw(long lotteryScheduleId, Date date);
}

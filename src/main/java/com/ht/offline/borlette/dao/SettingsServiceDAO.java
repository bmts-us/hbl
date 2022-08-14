/**
 * SettingsServiceDAO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import java.util.List;

import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.models.Settings;

public interface SettingsServiceDAO {
   Settings persistSettings(Settings settings);
   Settings loadSettings();
   LotterySchedule findLotterySchedule(long lotteryScheduleId);
   List<LotterySchedule> getLotterySchedules();
}
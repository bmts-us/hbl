/**
 * SettingsServiceDAOImpl.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import com.ht.offline.borlette.dao.repositories.LotteryScheduleRepository;
import com.ht.offline.borlette.dao.repositories.SettingsRepository;
import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.models.Settings;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("settingsServiceDAO")
public class SettingsServiceDAOImpl implements SettingsServiceDAO {

    final Logger log = LogManager.getLogger(SettingsServiceDAOImpl.class);

    @Autowired
    private SettingsRepository settingsRepository;
    
    @Autowired
    private LotteryScheduleRepository lotteryScheduleRepository;    

    //persist settings
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Settings persistSettings(Settings settings) {
        log.info("<>--------- inside persistSettings(Settings settings) ---------<>");

        try {
            log.info("<>--------- Saving settings.");
            //Get lottery schedules
            Set<LotterySchedule> lotterySchedules = settings.getLotterySchedules();
            //Re-set lottery schedules from settings
            settings.setLotterySchedules(null);
            
            //persist settings
            settings = settingsRepository.save(settings);
            
            final Settings params = settings; //to avoid lamda expression error
            
            //persist lottery schedules
            lotterySchedules.forEach((lotterySchedule) -> { 
            	lotterySchedule.setSettings(params);
            	lotteryScheduleRepository.save(lotterySchedule);
            });
            
            log.info("<>--------- Settings was saved successfully.");
        } 
        catch (Exception ex) {
            log.warn("?????? Unable to persist the object Settings :  persistSettings(Settings settings)", ex);
        }

        log.info("<>--------- Exit persistSettings(Settings settings) ---------<>");
        return settings;
    }

    //load settings 
    public Settings loadSettings() {
        log.info("<>--------- inside loadSettings() ---------<>");
        return this.settingsRepository.loadSettings();
    }
    
    //Find lottery schedule by id
    public LotterySchedule findLotterySchedule(long lotteryScheduleId) {
        log.info("<>--------- inside findLotterySchedule() ---------<>");
        return this.lotteryScheduleRepository.findById(lotteryScheduleId).orElse(null);
    }     

    //Find all lottery schedules
    public List<LotterySchedule> getLotterySchedules() {
        log.info("<>--------- inside getLotterySchedules() ---------<>");
        return this.lotteryScheduleRepository.findAll();
    }    
    
}
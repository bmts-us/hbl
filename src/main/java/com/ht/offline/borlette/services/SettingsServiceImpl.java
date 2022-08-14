/**
 * SettingsServiceImpl.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dao.SettingsServiceDAO;
import com.ht.offline.borlette.dtos.LotteryScheduleDTO;
import com.ht.offline.borlette.dtos.SettingsDTO;
import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.models.Settings;
import com.ht.offline.borlette.utils.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("settingsService")
public class SettingsServiceImpl implements SettingsService {
    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(SettingsServiceImpl.class);

    @Autowired
    protected SettingsServiceDAO settingsServiceDAO;

    //persist settingsDTO
    public Map<String, Object> persistSettings(SettingsDTO settingsDTO) {
        log.info("<>--------- inside persistSettings(SettingsDTO settingsDTO) ---------<>");

        Map<String, Object> persistMap = new HashMap<>();

        try {
        	
        	//load settings from the db
            Settings settings = settingsServiceDAO.loadSettings();

            if(Utils.isNotNull(settings)) {
            	settingsDTO.setSettingsId(settings.getSettingsId());
            }
            
            //parse settingsDTO into settings
            settings = SettingsDTO.parse().apply(settingsDTO);

            //persist settings into the database
            long settingsId = settingsServiceDAO.persistSettings(settings).getSettingsId();

            if(settingsId > 0) {
                persistMap.put("message", "message.code.007");
                settingsDTO.setSettingsId(settingsId);
                persistMap.put("settingsPersisted", settingsDTO);
            } 
            else persistMap.put("message", "error.code.011");
        } 
        catch (Exception ex) {
            persistMap.put("message", "error.code.011");
            log.error("???????????? Unable to persist the object Settings :  persistSettings(SettingsDTO settingsDTO)", ex);
        }

        return persistMap;
    }

    //method to load settings from the database
    public SettingsDTO loadSettings() {
        log.info("<>--------- inside loadSettings() ---------<>");  
        SettingsDTO settingsDTO = null;
        Settings settings = this.settingsServiceDAO.loadSettings();
        if(Utils.isNotNull(settings)) {
            //set lottery schedules' list into settings
            Set<LotteryScheduleDTO> lotterySchedules = new HashSet<LotteryScheduleDTO>();
            this.settingsServiceDAO.getLotterySchedules().forEach((lotterySchedule) -> {             	
            	LotteryScheduleDTO lotteryScheduleDTO = LotterySchedule.parse().apply(lotterySchedule);
            	lotterySchedules.add(lotteryScheduleDTO);
            });
            
            settingsDTO = Settings.parse().apply(settings);
            settingsDTO.setLotterySchedules(lotterySchedules);          	
            return settingsDTO;
        }
        else return null;
    }
    
    //Get settings as Json
    public JSONObject getSettingsAsJSon() {
        log.info("<>--------- inside getSettingsAsJSon()");  
        //load settings
        Settings settings = settingsServiceDAO.loadSettings();
        //get lottery schedule
        List<LotterySchedule> lotterySchedules = settingsServiceDAO.getLotterySchedules();
        
        JSONArray lotterySchedulesArray = new JSONArray();
        lotterySchedules.forEach((lotterySchedule) -> {             	
        	JSONObject lotteryScheduleObject = LotteryScheduleDTO.toJson().apply(lotterySchedule);
        	lotterySchedulesArray.put(lotteryScheduleObject); 	
        });
        
        JSONObject settingsObject = SettingsDTO.toJson().apply(settings);
        settingsObject.put("lotterySchedule", lotterySchedulesArray);        
        
        return settingsObject;
    }

}
/**
 * SettingsDTO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.models.Settings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.json.JSONObject;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * The transfer class
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettingsDTO {
    @JsonProperty("settingsId")
    private long settingsId;
     
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("lottoBoxWageDoubled")
    private boolean lottoBoxWageDoubled;

    @JsonProperty("lotteryWageSimple")
    private String lotteryWageSimple;

    @JsonProperty("lotteryWageMarriage")
    private double lotteryWageMarriage;
    
    @JsonProperty("lotteryWageLotto3")
    private double lotteryWageLotto3;    

    @JsonProperty("lotteryWageLotto4")
    private double lotteryWageLotto4;   
    
    @JsonProperty("lotterySchedules")
    private Set<LotteryScheduleDTO> lotterySchedules;
    
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a SettingsDTO to Settings
     * @return - function that will convert a settingsDTO to settings
     *-------------------------------------------------------------------------*/
    public static Function<SettingsDTO, Settings> parse() {
        return settingsDTO -> {
            Assert.notNull(settingsDTO, "SettingsDTO cannot be null.");
            Assert.notNull(settingsDTO.getLotterySchedules(), "LotterySchedules from DTO cannot be null.");
            Settings settings = new Settings();
            settings.setSettingsId(settingsDTO.getSettingsId());
            settings.setCurrency(settingsDTO.getCurrency());
            settings.setLottoBoxWageDoubled(settingsDTO.isLottoBoxWageDoubled());
            settings.setLotteryWageSimple(settingsDTO.getLotteryWageSimple());
            settings.setLotteryWageMarriage(settingsDTO.getLotteryWageMarriage());
            settings.setLotteryWageLotto3(settingsDTO.getLotteryWageLotto3());
            settings.setLotteryWageLotto4(settingsDTO.getLotteryWageLotto4());
            
            //set lottery schedules' list into settings
            Set<LotterySchedule> lotterySchedules = new HashSet<LotterySchedule>();
            settingsDTO.getLotterySchedules().forEach((lotteryScheduleDTO) -> {             	
            	LotterySchedule lotterySchedule = LotteryScheduleDTO.parse().apply(lotteryScheduleDTO);
            	lotterySchedules.add(lotterySchedule);
            });
            
            settings.setLotterySchedules(lotterySchedules);
            
            return settings;
        };
    }
    
    public static Function<Settings, JSONObject> toJson() {
        return settings -> {
            Assert.notNull(settings, "Settings cannot be null.");
            JSONObject settingsObject = new JSONObject();
            settingsObject.put("id", settings.getSettingsId());
            settingsObject.put("currency", settings.getCurrency());
            settingsObject.put("lottoBoxWageDoubled", settings.isLottoBoxWageDoubled());
            settingsObject.put("lotteryWageSimple", settings.getLotteryWageSimple());            
            settingsObject.put("lotteryWageMarriage", settings.getLotteryWageMarriage());
            settingsObject.put("lotteryWageLotto3", settings.getLotteryWageLotto3());
            settingsObject.put("lotteryWageLotto4", settings.getLotteryWageLotto4());
            return settingsObject;
        };
    }      
}

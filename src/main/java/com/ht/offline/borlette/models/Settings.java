/**
 * Settings.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.models;

import com.ht.offline.borlette.dtos.SettingsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;

/**
 * The persistent class for the Settings table.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema="adminsys")
public class Settings implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="settings_id", unique=true, nullable=false)
    private long settingsId;

    @Column(name="default_currency",nullable=false, length=3)
    private String currency;

    @Column(name="is_lottoboxwage_doubled")
    private boolean lottoBoxWageDoubled;

    @Column(name="lottery_wage_simple")
    private String lotteryWageSimple;

    @Column(name = "lottery_wage_marriage")
    private double lotteryWageMarriage;
    
    @Column(name = "lottery_wage_lotto3")
    private double lotteryWageLotto3;
    
    @Column(name = "lottery_wage_lotto4")
    private double lotteryWageLotto4; 
    
	//bi-directional many-to-one association to LotterySchedule
	@OneToMany(mappedBy="settings", cascade = { CascadeType.ALL })
	private Set<LotterySchedule> lotterySchedules;    
	
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a Settings to SettingsDTO
     * @return - function that will convert a settings to settingsDTO
     *-------------------------------------------------------------------------*/
    public static Function<Settings, SettingsDTO> parse() {
        return settings -> {
            //Assert.notNull(settings, "Settings cannot be null.");
            SettingsDTO settingsDTO = new SettingsDTO();
            settingsDTO.setSettingsId(settings.getSettingsId());
            settingsDTO.setCurrency(settings.getCurrency());
            settingsDTO.setLottoBoxWageDoubled(settings.isLottoBoxWageDoubled());
            settingsDTO.setLotteryWageSimple(settings.getLotteryWageSimple() );
            settingsDTO.setLotteryWageMarriage(settings.getLotteryWageMarriage());
            settingsDTO.setLotteryWageLotto3(settings.getLotteryWageLotto3());
            settingsDTO.setLotteryWageLotto4(settings.getLotteryWageLotto4());        
            
            return settingsDTO;
        };
    }
}

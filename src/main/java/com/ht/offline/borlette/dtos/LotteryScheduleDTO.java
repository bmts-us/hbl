/**
 * LotteryScheduleDTO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;

import java.io.Serializable;
import java.util.function.Function;

import org.json.JSONObject;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.utils.DateUtils;

import biz.isman.util.ConvertUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the LotteryScheduleDTO table.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotteryScheduleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("lotteryScheduleId")
    private long lotteryScheduleId;

	@JsonProperty("removed")
    private boolean removed;

	@JsonProperty("name")
    private String name;

	@JsonProperty("time")
    private String time;

	@JsonProperty("closeTime")
    private String closeTime;
	
	@JsonProperty("meridian")
    private String meridian; //midday, evening

	@JsonProperty("freeMarriageAllowed")
    private boolean freeMarriageAllowed;

	@JsonProperty("boxAllowed")
    private boolean boxAllowed;

	@JsonProperty("ticketNotes")
    private String ticketNotes; 	
    
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a LotteryScheduleDTO to LotterySchedule
     * @return - function that will convert a LotteryScheduleDTO to LotterySchedule
     *-------------------------------------------------------------------------*/
    public static Function<LotteryScheduleDTO, LotterySchedule> parse() {
        return lotteryScheduleDTO -> {
            Assert.notNull(lotteryScheduleDTO, "LotteryScheduleDTO cannot be null.");
            LotterySchedule lotterySchedule = new LotterySchedule();
            lotterySchedule.setLotteryScheduleId(lotteryScheduleDTO.getLotteryScheduleId());
            lotterySchedule.setRemoved(lotteryScheduleDTO.isRemoved());
            lotterySchedule.setName(lotteryScheduleDTO.getName());
            lotterySchedule.setTime(ConvertUtils.isNotBlank(lotteryScheduleDTO.getTime())
            		? DateUtils.parseStringToTime(lotteryScheduleDTO.getTime(), "hh:mm a"):null);
            lotterySchedule.setCloseTime(ConvertUtils.isNotBlank(lotteryScheduleDTO.getCloseTime())
            		? DateUtils.parseStringToTime(lotteryScheduleDTO.getCloseTime(), "hh:mm a"):null);
            lotterySchedule.setMeridian(lotteryScheduleDTO.getMeridian());
            lotterySchedule.setFreeMarriageAllowed(lotteryScheduleDTO.isFreeMarriageAllowed());
            lotterySchedule.setBoxAllowed(lotteryScheduleDTO.isBoxAllowed());
            lotterySchedule.setTicketNotes(lotteryScheduleDTO.getTicketNotes());
            return lotterySchedule;
        };
    }   
    
    public static Function<LotterySchedule, JSONObject> toJson() {
        return lotterySchedule -> {
            Assert.notNull(lotterySchedule, "LotterySchedule cannot be null.");
            JSONObject lotteryScheduleObject = new JSONObject();
            lotteryScheduleObject.put("id", lotterySchedule.getLotteryScheduleId());
            lotteryScheduleObject.put("removed", lotterySchedule.isRemoved());
            lotteryScheduleObject.put("name", lotterySchedule.getName());
            lotteryScheduleObject.put("closeTime", lotterySchedule.getCloseTime());
            lotteryScheduleObject.put("meridian", lotterySchedule.getMeridian());
            lotteryScheduleObject.put("freeMarriageAllowed", lotterySchedule.isFreeMarriageAllowed());
            lotteryScheduleObject.put("boxAllowed", lotterySchedule.isBoxAllowed());
            lotteryScheduleObject.put("ticketNotes", lotterySchedule.getTicketNotes());            
            return lotteryScheduleObject;
        };
    }      

}

/**
 * LotteryDrawDTO.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ht.offline.borlette.models.LotteryDraw;
import com.ht.offline.borlette.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotteryDrawDTO implements Serializable, Comparable<LotteryDrawDTO> {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("lotteryDrawId")
	private long lotteryDrawId;
	
	@JsonProperty("date")
	private Date date;
	
	@JsonProperty("drawDate")
	private String drawDate; //to display

	@JsonProperty("rank")
	private int rank; //rank of the result if lottery type is simple. Otherwise, it will remain 0.
	
	@JsonProperty("cashThree")
	private String cashThree;
	
	@JsonProperty("playFour")
	private String playFour;
	
	@JsonProperty("lotteryScheduleId")
	private long lotteryScheduleId;
		
	private LotteryScheduleDTO lotterySchedule;
	
	private StampDTO stamp;
	
	@JsonProperty("drawn")
	private boolean drawn;
	
	@JsonProperty("reversible")
	private boolean reversible; 	

	//method to sort lottery draw by rank
	public int compareTo(LotteryDrawDTO lotteryDraw) {		 
		int rank = lotteryDraw.getRank();
 
		//ascending order
		return ((Integer)this.getRank()).compareTo(rank);
	}	
	
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a LotteryDrawDTO to LotteryDraw
     * @return - function that will convert a lotteryDrawDTO to lotteryDraw
     *-------------------------------------------------------------------------*/
    public static Function<LotteryDrawDTO, LotteryDraw> parse() {
        return lotteryDrawDTO -> {
            Assert.notNull(lotteryDrawDTO, "LotteryDrawDTO cannot be null.");
            LotteryDraw lotteryDraw = new LotteryDraw();
            lotteryDraw.setLotteryDrawId(lotteryDrawDTO.getLotteryDrawId());
            lotteryDraw.setDate(lotteryDrawDTO.getDate());
            lotteryDraw.setCashThree(lotteryDrawDTO.getCashThree());
            lotteryDraw.setPlayFour(lotteryDrawDTO.getPlayFour());
            
            LotteryScheduleDTO lotteryScheduleDTO = new LotteryScheduleDTO();
            lotteryScheduleDTO.setLotteryScheduleId(lotteryDrawDTO.getLotteryScheduleId());            
            lotteryDraw.setLotterySchedule(LotteryScheduleDTO.parse().apply(lotteryScheduleDTO));  
            
			if(Utils.isNotNull(lotteryDrawDTO.getStamp())) {
				lotteryDraw.setCreatedBy(lotteryDrawDTO.getStamp().getCreatedBy());
				lotteryDraw.setCreatedDate(lotteryDrawDTO.getStamp().getCreatedDate());
				lotteryDraw.setCreatedTime(lotteryDrawDTO.getStamp().getCreatedTime());
				lotteryDraw.setDateStamp(lotteryDrawDTO.getStamp().getDateStamp());
				lotteryDraw.setTimeStamp(lotteryDrawDTO.getStamp().getTimeStamp());
				lotteryDraw.setUserStamp(lotteryDrawDTO.getStamp().getUserStamp());	
			}            
            
            return lotteryDraw;
        };
    }	
}

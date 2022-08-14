/**
 * LotteryDraw.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.models;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.util.Assert;

import com.ht.offline.borlette.dtos.LotteryDrawDTO;
import com.ht.offline.borlette.dtos.StampDTO;
import com.ht.offline.borlette.utils.DateUtils;

import biz.isman.util.ConvertUtils;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * The persistent class for the lottery_draw database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema="adminsys")
public class LotteryDraw implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lottery_draw_id", unique=true, nullable=false)
	private long lotteryDrawId;
	
	private Date date;

	@Column(name="cash_three")
	private String cashThree;
	
	@Column(name="play_four")
	private String playFour;
	
	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Time createdTime;

	@Temporal(TemporalType.DATE)
	@Column(name="date_stamp")
	private Date dateStamp;

	@Column(name="time_stamp")
	private Time timeStamp;

	@Column(name="user_stamp")
	private String userStamp;	
	
	//bi-directional many-to-one association to LotterySchedule
	@ManyToOne
	@JoinColumn(name="lottery_schedule_id")
	private LotterySchedule lotterySchedule;	
	
	@Transient
	List<Ticket> ticketsWon; //hold tickets won to compute while persisting draw. It should be in an ACID transaction, if tickets won are not persisted lottery draw will not too. 

    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a LotteryDraw to LotteryDrawDTO
     * @return - function that will convert a lotteryDraw to lotteryDrawDTO
     *-------------------------------------------------------------------------*/
    public static Function<LotteryDraw, LotteryDrawDTO> parse() {
        return lotteryDraw -> {
            Assert.notNull(lotteryDraw, "LotteryDraw cannot be null.");
            LotteryDrawDTO lotteryDrawDTO = new LotteryDrawDTO();
            lotteryDrawDTO.setLotteryDrawId(lotteryDraw.getLotteryDrawId());
            lotteryDrawDTO.setDate(lotteryDraw.getDate());
            lotteryDrawDTO.setCashThree(lotteryDraw.getCashThree());
            lotteryDrawDTO.setPlayFour(lotteryDraw.getPlayFour());
            
            lotteryDrawDTO.setLotteryScheduleId(lotteryDraw.getLotterySchedule().getLotteryScheduleId());
            //If cause overflow, remove it
            lotteryDrawDTO.setLotterySchedule(LotterySchedule.parse().apply(lotteryDraw.getLotterySchedule()));
            
			if(ConvertUtils.isNotBlank(lotteryDraw.getCashThree()) 
				&&  ConvertUtils.isNotBlank(lotteryDraw.getPlayFour())) {
				lotteryDrawDTO.setDrawn(true);						
			}             
            
			StampDTO stampDTO = new StampDTO();
			
			stampDTO.setCreatedBy(lotteryDraw.getCreatedBy());
			stampDTO.setCreatedDate(lotteryDraw.getCreatedDate());
			stampDTO.setCreatedTime(lotteryDraw.getCreatedTime());
			stampDTO.setDateStamp(lotteryDraw.getDateStamp());
			stampDTO.setTimeStamp(lotteryDraw.getTimeStamp());
			stampDTO.setUserStamp(lotteryDraw.getUserStamp());			
			stampDTO.setCreatedOn(DateUtils.getShortDateEn(lotteryDraw.getCreatedDate()));
			stampDTO.setStampOn(DateUtils.getShortDateEn(lotteryDraw.getDateStamp()));
			stampDTO.setCreatedAt(DateUtils.timeToString(lotteryDraw.getCreatedTime(), "hh:mm:ss a"));
			stampDTO.setStampAt(DateUtils.timeToString(lotteryDraw.getTimeStamp(), "hh:mm:ss a"));
			
			lotteryDrawDTO.setStamp(stampDTO);            
            return lotteryDrawDTO;
        };
    }	
}
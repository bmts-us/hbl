/**
 * LotteryScheduleDTO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import java.util.function.Function;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.ht.offline.borlette.dtos.LotteryScheduleDTO;
import com.ht.offline.borlette.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the LotteryScheduleDTO table.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema="adminsys")
public class LotterySchedule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lottery_schedule_id", unique=true, nullable=false)
    private long lotteryScheduleId;

    @Column(name="is_removed")
    private boolean removed;
    
    @Column(name="lottery_name",nullable=false)
    private String name;

    private Time time;   

    @Column(name="close_time")
    private Time closeTime;
    
    private String meridian; //midday, evening

    @Column(name = "is_free_marriage_allowed")
    private boolean freeMarriageAllowed;
    
    @Column(name = "is_box_allowed")
    private boolean boxAllowed;
    
    @Column(name = "ticket_notes")
    private String ticketNotes; 	
    
	//bi-directional many-to-one association to Settings
	@ManyToOne
	@JoinColumn(name="settings_id")
	private Settings settings;
	
	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="lotterySchedule", cascade = { CascadeType.ALL })
	private List<Ticket> tickets;
	
	//bi-directional many-to-one association to LotteryDraw
	@OneToMany(mappedBy="lotterySchedule", cascade = { CascadeType.ALL })
	private List<LotteryDraw> lotteryDraws;	
    
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a LotterySchedule to LotteryScheduleDTO
     * @return - function that will convert a LotterySchedule to LotteryScheduleDTO
     *-------------------------------------------------------------------------*/
    public static Function<LotterySchedule, LotteryScheduleDTO> parse() {
        return lotterySchedule -> {
            Assert.notNull(lotterySchedule, "LotterySchedule cannot be null.");
            LotteryScheduleDTO lotteryScheduleDTO = new LotteryScheduleDTO();
            lotteryScheduleDTO.setLotteryScheduleId(lotterySchedule.getLotteryScheduleId());
            lotteryScheduleDTO.setRemoved(lotterySchedule.isRemoved());
            lotteryScheduleDTO.setName(lotterySchedule.getName());
            //lotteryScheduleDTO.setCloseTime(lotterySchedule.getCloseTime() );
            lotteryScheduleDTO.setTime(DateUtils.timeToString(lotterySchedule.getTime(), "hh:mm a"));
            lotteryScheduleDTO.setCloseTime(DateUtils.timeToString(lotterySchedule.getCloseTime(), "hh:mm a"));
            lotteryScheduleDTO.setMeridian(lotterySchedule.getMeridian());
            lotteryScheduleDTO.setFreeMarriageAllowed(lotterySchedule.isFreeMarriageAllowed());
            lotteryScheduleDTO.setBoxAllowed(lotterySchedule.isBoxAllowed());
            lotteryScheduleDTO.setTicketNotes(lotterySchedule.getTicketNotes());
            return lotteryScheduleDTO;
        };
    }    

}

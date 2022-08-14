/**
 * Ticket.java
 * Created on 2022-07-25
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
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

import com.ht.offline.borlette.dtos.StampDTO;
import com.ht.offline.borlette.dtos.TicketDTO;
import com.ht.offline.borlette.dtos.TicketDetailsDTO;
import com.ht.offline.borlette.utils.DateUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;


/**
 * The persistent class for the tickets database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema="adminsys")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id", unique=true, nullable=false)
	private long ticketId;

	@Temporal(TemporalType.DATE)
	private Date date;	
	
	private double amount;

	@Column(name="is_payment_made")
	private boolean paymentMade;

	@Column(name="is_won")
	private boolean won;

	@Column(name="ticket_number")
	private String ticketNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_stamp")
	private Date dateStamp;	

	@Column(name="time_stamp")
	private Time timeStamp;

	@Column(name="user_stamp")
	private String userStamp;
	
	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Time createdTime;	

	//bi-directional many-to-one association to LotterySchedule
	@ManyToOne
	@JoinColumn(name="lottery_schedule_id")
	private LotterySchedule lotterySchedule;
	
	//bi-directional many-to-one association to Agent
	@ManyToOne
	@JoinColumn(name="agent_id")
	private Agent agent;

	//bi-directional many-to-one association to TicketsDetails
	@OneToMany(mappedBy="ticket")
	private List<TicketDetails> ticketDetails;

    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a Ticket to TicketDTO
     * @return - function that will convert a Ticket to TicketDTO
     *-------------------------------------------------------------------------*/
    public static Function<Ticket, TicketDTO> parse() {
        return ticket -> {
            Assert.notNull(ticket, "Ticket cannot be null.");
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setTicketId(ticket.getTicketId());
            ticketDTO.setDate(ticket.getDate());
            ticketDTO.setTicketDate(DateUtils.dateToString(ticket.getDate(), "yyyy-MM-dd"));
            ticketDTO.setCreatedTime(DateUtils.timeToString(ticket.getCreatedTime(), "hh:mm:ss"));
            ticketDTO.setAmount(ticket.getAmount());
            ticketDTO.setPaymentMade(ticket.isPaymentMade() );
            ticketDTO.setWon(ticket.isWon());
            ticketDTO.setTicketNumber(ticket.getTicketNumber());
            ticketDTO.setAgentId(ticket.getAgent().getAgentId());
            ticketDTO.setAgent(Agent.parse().apply(ticket.getAgent()));            
            ticketDTO.setLotteryScheduleId(ticket.getLotterySchedule().getLotteryScheduleId());
            ticketDTO.setLotterySchedule(LotterySchedule.parse().apply(ticket.getLotterySchedule()));            
            
			List<TicketDetailsDTO> ticketsDetails = new ArrayList<TicketDetailsDTO>();

			ticket.getTicketDetails().forEach((ticketDetail) -> {   
				TicketDetailsDTO ticketDetailsDTO = TicketDetails.parse().apply(ticketDetail);
            	ticketsDetails.add(ticketDetailsDTO);
            });
			
			ticketDTO.setTicketDetails(ticketsDetails);	
			
			StampDTO stampDTO = new StampDTO();
			
			stampDTO.setCreatedBy(ticket.getCreatedBy());
			stampDTO.setCreatedDate(ticket.getCreatedDate());
			stampDTO.setCreatedTime(ticket.getCreatedTime());
			stampDTO.setDateStamp(ticket.getDateStamp());
			stampDTO.setTimeStamp(ticket.getTimeStamp());
			stampDTO.setUserStamp(ticket.getUserStamp());			
			stampDTO.setCreatedOn(DateUtils.getShortDateEn(ticket.getCreatedDate()));
			stampDTO.setStampOn(DateUtils.getShortDateEn(ticket.getDateStamp()));
			stampDTO.setCreatedAt(DateUtils.timeToString(ticket.getCreatedTime(), "hh:mm:ss a"));
			stampDTO.setStampAt(DateUtils.timeToString(ticket.getTimeStamp(), "hh:mm:ss a"));            
			ticketDTO.setStamp(stampDTO);
			
            return ticketDTO;
        };
    }	
}
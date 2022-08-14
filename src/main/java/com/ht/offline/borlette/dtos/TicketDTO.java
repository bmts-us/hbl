/**
 * TicketDTO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ht.offline.borlette.models.Ticket;
import com.ht.offline.borlette.models.TicketDetails;
import com.ht.offline.borlette.utils.Utils;

import biz.isman.util.ConvertUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("ticketId")
	private long ticketId;

	@JsonProperty("date")
	private Date date;	
	
	@JsonProperty("createdTime")
	private String createdTime;	
	
	@JsonProperty("ticketDate")
	private String ticketDate; //to display
	
	@JsonProperty("amount")
	private double amount;

	@JsonProperty("paymentMade")
	private boolean paymentMade;

	@JsonProperty("won")
	private boolean won;

	@JsonProperty("ticketNumber")
	private String ticketNumber;

	@JsonProperty("agentId")
	private long agentId;
	
	private AgentDTO agent;
		
	@JsonProperty("lotteryScheduleId")
	private long lotteryScheduleId;
		
	private LotteryScheduleDTO lotterySchedule;
	
	@JsonProperty("ticketDetails")
	private List<TicketDetailsDTO> ticketDetails;
	
	private StampDTO stamp;
	
	@JsonProperty("totalAmount")
	private String totalAmount;
	
	@JsonProperty("amountWon")
	private double amountWon;
	
	@JsonProperty("totalAmountWon")
	private String totalAmountWon;

    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a TicketDTO to Ticket
     * @return - function that will convert a TicketDTO to Ticket
     *-------------------------------------------------------------------------*/
    public static Function<TicketDTO, Ticket> parse() {
        return ticketDTO -> {
            Assert.notNull(ticketDTO, "TicketDTO cannot be null.");
            Ticket ticket = new Ticket();
            ticket.setTicketId(ticketDTO.getTicketId());
            ticket.setDate(Utils.isNotNull(ticketDTO.getDate())
            		? ticketDTO.getDate() : ConvertUtils.getDate(ticketDTO.getTicketDate()));
            ticket.setAmount(ticketDTO.getAmount());
            ticket.setWon(ticketDTO.isWon());
            ticket.setPaymentMade(ticketDTO.isPaymentMade());
            ticket.setTicketNumber(ticketDTO.getTicketNumber());
            
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentId(ticketDTO.getAgentId());            
            ticket.setAgent(AgentDTO.parse().apply(agentDTO));
            
            LotteryScheduleDTO lotteryScheduleDTO = new LotteryScheduleDTO();
            lotteryScheduleDTO.setLotteryScheduleId(ticketDTO.getLotteryScheduleId());            
            ticket.setLotterySchedule(LotteryScheduleDTO.parse().apply(lotteryScheduleDTO));
            
            //set ticketDetails' list into ticket
            List<TicketDetails> ticketDetails = new ArrayList<TicketDetails>();
            ticketDTO.getTicketDetails().forEach((ticketDetailsDTO) -> {             	
            	TicketDetails ticketDetail = TicketDetailsDTO.parse().apply(ticketDetailsDTO);
            	ticketDetails.add(ticketDetail);
            });
            
            ticket.setTicketDetails(ticketDetails);  
            
			if(Utils.isNotNull(ticketDTO.getStamp())) {
				ticket.setCreatedBy(ticketDTO.getStamp().getCreatedBy());
				ticket.setCreatedDate(ticketDTO.getStamp().getCreatedDate());
				ticket.setCreatedTime(ticketDTO.getStamp().getCreatedTime());
				ticket.setDateStamp(ticketDTO.getStamp().getDateStamp());
				ticket.setTimeStamp(ticketDTO.getStamp().getTimeStamp());
				ticket.setUserStamp(ticketDTO.getStamp().getUserStamp());	
			}  
			
            return ticket;
        };
    }
}
/**
 * TicketDetailsDTO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;

import java.io.Serializable;
import java.util.function.Function;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ht.offline.borlette.models.TicketDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDetailsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("ticketDetailsId")
	private long ticketDetailsId;
	
	@JsonProperty("amount")
	private double amount;
	
	@JsonProperty("amountWon")
	private double amountWon;
	
	@JsonProperty("betOn")
	private String betOn;
	
	@JsonProperty("won")
	private boolean won;
	
	@JsonProperty("betAmountWon")
	private String betAmountWon;
	
	@JsonProperty("betAmount")
	private String betAmount;
	
	@JsonProperty("rank")
	private int rank;
	
	@JsonProperty("type")
	private String type; //lt.sp, lt.ma, lt.l3, lt.l4, etc...
	
	private TicketDTO ticket;
	
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a ticketDetailsDTO to TicketDetails
     * @return - function that will convert a ticketDetailsDTO to TicketDetails
     *-------------------------------------------------------------------------*/
    public static Function<TicketDetailsDTO, TicketDetails> parse() {
        return ticketDetailsDTO -> {
            Assert.notNull(ticketDetailsDTO, "TicketDetailsDTO cannot be null.");
            TicketDetails ticketDetails = new TicketDetails();
            ticketDetails.setTicketDetailsId(ticketDetailsDTO.getTicketDetailsId());
            ticketDetails.setAmount(ticketDetailsDTO.getAmount());
            ticketDetails.setAmountWon(ticketDetailsDTO.getAmountWon());
            ticketDetails.setBetOn(ticketDetailsDTO.getBetOn() );
            ticketDetails.setWon(ticketDetailsDTO.isWon());
            ticketDetails.setRank(ticketDetailsDTO.getRank());
            ticketDetails.setType(ticketDetailsDTO.getType());
            return ticketDetails;
        };
    }	

}
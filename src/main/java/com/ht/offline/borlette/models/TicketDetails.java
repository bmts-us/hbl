/**
 * TicketDetails.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.models;

import java.io.Serializable;
import java.util.function.Function;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.ht.offline.borlette.dtos.TicketDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

/**
 * The persistent class for the ticket_details database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="ticket_details", schema="adminsys")
public class TicketDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_details_id", unique=true, nullable=false)
	private long ticketDetailsId;

	private double amount;

	@Column(name="amount_won")
	private double amountWon;

	@Column(name="bet_on")
	private String betOn;

	@Column(name="is_won")
	private boolean won;

	private int rank;
	
	private String type;

	//bi-directional many-to-one association to Ticket
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private Ticket ticket;

    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a ticketDetails to TicketDetailsDTO
     * @return - function that will convert a ticketDetails to TicketDetailsDTO
     *-------------------------------------------------------------------------*/
    public static Function<TicketDetails, TicketDetailsDTO> parse() {
        return ticketDetails -> {
            Assert.notNull(ticketDetails, "TicketDetails cannot be null.");
            TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
            ticketDetailsDTO.setTicketDetailsId(ticketDetails.getTicketDetailsId());
            ticketDetailsDTO.setAmount(ticketDetails.getAmount());
            ticketDetailsDTO.setAmountWon(ticketDetails.getAmountWon());
            ticketDetailsDTO.setBetOn(ticketDetails.getBetOn() );            
            ticketDetailsDTO.setWon(ticketDetails.isWon());
            ticketDetailsDTO.setRank(ticketDetails.getRank());
            ticketDetailsDTO.setType(ticketDetails.getType());
            return ticketDetailsDTO;
        };
    }	
}
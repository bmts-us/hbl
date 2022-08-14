/**
 * TicketWrapperDTO.java
 * Created on 2022-07-27
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketWrapperDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long agentId;
	
	private AgentDTO agent;
	
	private List<TicketDTO> tickets;
	
	private double amountSold;
	
	private String sumSold;
	
	private double amountWon;
	
	private String sumWon;
	
	private boolean won;
	
	private double profitAmount;
	
	private String benefitAmount; //to display the profit amount
	
	private boolean profit;
	
	private String currency;
	
	private String earnings;
	
	private LotteryScheduleDTO lotterySchedule;	

}

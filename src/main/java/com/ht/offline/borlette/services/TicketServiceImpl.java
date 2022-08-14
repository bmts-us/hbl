/**
 * TicketServiceImpl.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dao.TicketServiceDAO;
import com.ht.offline.borlette.dtos.AgentDTO;
import com.ht.offline.borlette.dtos.LotteryScheduleDTO;
import com.ht.offline.borlette.dtos.SettingsDTO;
import com.ht.offline.borlette.dtos.StampDTO;
import com.ht.offline.borlette.dtos.TicketDTO;
import com.ht.offline.borlette.dtos.TicketDetailsDTO;
import com.ht.offline.borlette.dtos.TicketWrapperDTO;
import com.ht.offline.borlette.models.Ticket;
import com.ht.offline.borlette.utils.Utils;

import biz.isman.util.ConvertUtils;
import biz.isman.util.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {
    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(TicketServiceImpl.class);

    @Autowired
    protected TicketServiceDAO ticketServiceDAO;

    @Autowired
    protected SettingsService settingService;
    
    @Autowired
    protected CommonService commonService;
    
    @Autowired
    protected AgentService agentService; 
    
    @Autowired
    protected SettingsService settingsService;     
    
    //persist ticketDTO
    public Map<String, Object> persistTicket(TicketDTO ticketDTO) {
        log.info("<>--------- inside persistTicket(TicketDTO ticketDTO) ---------<>");

        Map<String, Object> persistMap = new HashMap<>();

        try {
			double totalAmount = 0.00;	
			
			//all tickets must be new, so set ticket key to 0.
			ticketDTO.setTicketId(0);
			
			for(TicketDetailsDTO ticketDetails: ticketDTO.getTicketDetails()) {
				totalAmount=  totalAmount+(ConvertUtils.isSame(ticketDetails.getType(), "lt.fm") 
							  ? 0 : ticketDetails.getAmount());
			}
			
			//set total amount into ticket
			ticketDTO.setAmount(totalAmount);			
        	
			//create lottery draw stamp
			StampDTO stampDTO = this.ticketStamp(null, "daltonh");
			ticketDTO.setStamp(stampDTO);
                        
            //parse ticketDTO into ticket
        	Ticket ticket = TicketDTO.parse().apply(ticketDTO);

            //persist ticket into the database
            long ticketId = ticketServiceDAO.persistTicket(ticket).getTicketId();

            if(ticketId > 0) {
                persistMap.put("message", "message.code.007");
                ticketDTO.setTicketId(ticketId);
                persistMap.put("ticketPersisted", ticketDTO);
            } 
            else persistMap.put("message", "error.code.011");
        } 
        catch (Exception ex) {
            persistMap.put("message", "error.code.011");
            log.error("???????????? Unable to persist the object Ticket :  persistTicket(TicketDTO ticketDTO)", ex);
        }

        return persistMap;
    }

    //persist tickets
    public boolean persistTicket(List<TicketDTO> ticketsList) {
        log.info("<>--------- inside persistTicket(List<TicketDTO> ticketsList) ---------<>");

        boolean persisted = false;

        try {
        	List<Ticket> tickets = new ArrayList<>();
        	
			for (TicketDTO ticketDTO : ticketsList) {
				//double totalAmount = 0.00;

				// all tickets must be new, so set ticket key to 0.
				ticketDTO.setTicketId(0);

				/*for (TicketDetailsDTO ticketDetails : ticketDTO.getTicketDetails()) {
					totalAmount = totalAmount
							+ (ConvertUtils.isSame(ticketDetails.getType(), "lt.fm") ? 0 : ticketDetails.getAmount());
				}

				// set total amount into ticket
				ticketDTO.setAmount(totalAmount);*/

				// create ticket stamp
				StampDTO stampDTO = this.ticketStamp(null, "daltonh");
				ticketDTO.setStamp(stampDTO);

				// parse ticketDTO into ticket
				tickets.add(TicketDTO.parse().apply(ticketDTO));
			}

            //persist tickets into the database
            persisted = ticketServiceDAO.persistTicket(tickets);            
        } 
        catch (Exception ex) {
            log.error("???????????? Unable to persist the Tickets' list :  persistTicket(List<TicketDTO> ticketsList)", ex);
        }

        return persisted;
    }
    
    //persist ticket payment
    public boolean persistPayment(long ticketId) {
        log.info("<>--------- inside persistPayment(long ticketId) ---------<>");

        boolean paid = false;

        try {
        	TicketDTO ticketDTO = this.loadTicket(ticketId);
        	if(Utils.isNotNull(ticketDTO)) {
        		//change payment status
        		ticketDTO.setPaymentMade(true);
				//create ticket stamp
				StampDTO stampDTO = this.ticketStamp(ticketDTO, "daltonh");
				ticketDTO.setStamp(stampDTO);
	            //persist ticket payment into the database
				ticketServiceDAO.persistTicket(TicketDTO.parse().apply(ticketDTO)); 
				paid = true;
			}          
        } 
        catch (Exception ex) {
            log.error("???????????? Unable to persist the Ticket payment :  persistPayment(long ticketId)", ex);
        }

        return paid;
    }    
    
    //method to load ticket from the database
    public TicketDTO loadTicket(long ticketId) {
        log.info("<>--------- inside loadTicket(long ticketId) ---------<>");        
        Ticket ticket = this.ticketServiceDAO.loadTicket(ticketId);
        if(Utils.isNotNull(ticket))
            return Ticket.parse().apply(ticket);
        else 
        	return null;
    }
    
    public List<TicketDTO> fetchTickets(long lotteryScheduleId, Date date) {
        log.info("<>--------- inside fetchTickets()");
        List<TicketDTO> tickets = null;
        try {
            List<Ticket> ticketsList = ticketServiceDAO.getTickets(lotteryScheduleId, date);

            if(Utils.isNotNull(ticketsList)) {
                tickets = new ArrayList<>();
                TicketDTO ticketDTO;
                for(Ticket ticket: ticketsList) {
                    ticketDTO = Ticket.parse().apply(ticket);
                    tickets.add(ticketDTO);
                }
            }
        } 
        catch (Exception ex) {
            log.warn("???????????? Unable to get tickets' list. ", ex);
        }

        log.info("<>--------- Exit fetchTickets()");
        return tickets;
    }   
    
    //Get tickets of a specific agent
    public List<TicketDTO> getTickets(long agentId, long lotteryScheduleId, Date date) {
        log.info("<>--------- inside getTickets(...)");
        List<TicketDTO> tickets = null;
        try {
            List<Ticket> ticketsList = ticketServiceDAO.getTickets(agentId, lotteryScheduleId, date);

            if(Utils.isNotNull(ticketsList)) {
                tickets = new ArrayList<>();
                TicketDTO ticketDTO;
                for(Ticket ticket: ticketsList) {
                    ticketDTO = Ticket.parse().apply(ticket);
                    ticketDTO.setAgent(null);
                    ticketDTO.setLotterySchedule(null);
                    ticketDTO.setStamp(null);
                    tickets.add(ticketDTO);
                }
            }
        } 
        catch (Exception ex) {
            log.warn("???????????? Unable to get tickets' manifest. ", ex);
        }

        log.info("<>--------- Exit getTicketsManifest(...)");
        return tickets;
    }      
    
	//get tickets sold of lottery from the database
	public List<TicketWrapperDTO> getTickets(long lotteryScheduleId, Date date) {
		log.info("<>------------- inside getTickets(long lotteryScheduleId, Date date) ---------<>");
		List<TicketWrapperDTO> tickets = null;		
		//Get the tickets from the database
		List<TicketDTO> ticketsList = this.fetchTickets(lotteryScheduleId, date);		
		try {	
			double totalAmount = 0.00, ticketAmountWon = 0.00;  
			
			if(Utils.isNotNull(ticketsList)) {
				SettingsDTO settings = settingsService.loadSettings();
				TicketWrapperDTO ticketWrapper = null;
				LotteryScheduleDTO lotterySchedule = null;
				tickets = new ArrayList<TicketWrapperDTO>();
				double amountSold = 0.00, amountWon = 0.00, profitAmount = 0.00;
				int index = 0;
				String currency = settings.getCurrency();
				
				List<AgentDTO> agents = agentService.getAgents();
				for(AgentDTO agent: agents) {
					ticketWrapper = new TicketWrapperDTO();
					ticketWrapper.setTickets(new ArrayList<TicketDTO>());
					amountSold = 0.00; //hold the total amount sold of the lottery by agent.
					amountWon = 0.00;  //hold the total amount won of the lottery by agent.
					profitAmount = 0.00;
					lotterySchedule = null;
					
					for(TicketDTO ticket: ticketsList) {
						if(ConvertUtils.isSame(agent.getAgentId(), ticket.getAgentId())) {	
							if(Utils.isNull(lotterySchedule)) {
								lotterySchedule = ticket.getLotterySchedule();
							}
							
							ticketAmountWon = 0.00;
							totalAmount = 0.00; 
							index = 0;	
							
							for(TicketDetailsDTO ticketDetailsDTO: ticket.getTicketDetails()) {								
								totalAmount=  totalAmount+(ConvertUtils.isSame(ticketDetailsDTO.getType(), "lt.fm") 
											  ? 0 : ticketDetailsDTO.getAmount());
								ticketAmountWon = ticketAmountWon + ticketDetailsDTO.getAmountWon(); //calculate the ticket total amount won
								ticketDetailsDTO.setBetAmount(new Money(ticketDetailsDTO.getAmount(), currency).toString());
								ticketDetailsDTO.setBetAmountWon(new Money(ticketDetailsDTO.getAmountWon(), currency).toString());
								ticket.getTicketDetails().set(index, ticketDetailsDTO);
								index++;
							}
									
							ticket.setAmount(totalAmount);
							ticket.setTotalAmount(new Money(totalAmount, currency).toString());							
									
							//compute total amount won
							if(ticket.isWon()) {
								ticket.setAmountWon(ticketAmountWon);									
								ticket.setTotalAmountWon(new Money(ticketAmountWon, currency).toString());							
							}	
								
							amountSold = amountSold + totalAmount;
							amountWon = amountWon + ticketAmountWon;
							profitAmount = amountSold - amountWon;
							
							ticketWrapper.getTickets().add(ticket);							
						}
					}
					
					if(!ticketWrapper.getTickets().isEmpty()) {
						ticketWrapper.setAgentId(agent.getAgentId());
						ticketWrapper.setAgent(agent);
						ticketWrapper.setAmountSold(amountSold);
						ticketWrapper.setSumSold(new Money(amountSold, currency).toString());
						ticketWrapper.setAmountWon(amountWon);
						ticketWrapper.setSumWon(new Money(amountWon, currency).toString());
						ticketWrapper.setProfitAmount(profitAmount);
						ticketWrapper.setBenefitAmount(new Money(profitAmount, currency).toString());
						ticketWrapper.setCurrency(currency);
						ticketWrapper.setLotterySchedule(lotterySchedule);
						tickets.add(ticketWrapper);
					}
				}
			}		
        } 
        catch (Exception ex) {
            log.warn("???????????? Unable to get tickets' list. ", ex);
        }
		
		log.info("<>------------- exit getTickets(...) ---------<>");
		return Utils.isNotNull(tickets) ? !tickets.isEmpty() ? tickets:null:null;
	}
    
    
	//Ticket stamps, @ticketDTO : this argument value must be come from the db.
	private StampDTO ticketStamp(TicketDTO ticketDTO, String connectedUser) {									
		Map<String, Object> ticketStamp = new HashMap<String, Object>();
			
		if(Utils.isNotNull(ticketDTO)) {
			ticketStamp.put("key", ticketDTO.getTicketId());	
			if(Utils.isNotNull(ticketDTO.getStamp())) {
				ticketStamp.put("createdDate", ticketDTO.getStamp().getCreatedDate());
				ticketStamp.put("createdTime", ticketDTO.getStamp().getCreatedTime());
				ticketStamp.put("createdBy", ticketDTO.getStamp().getCreatedBy());	
			} else ticketStamp.put("key", 0L);
		} else ticketStamp.put("key", 0L);
			
		ticketStamp.put("connectedUser", connectedUser);
								
		return commonService.populateStamp(ticketStamp);
	}     

}
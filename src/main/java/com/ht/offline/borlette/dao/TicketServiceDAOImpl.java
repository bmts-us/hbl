/**
 * TicketServiceDAOImpl.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import com.ht.offline.borlette.dao.repositories.LotteryScheduleRepository;
import com.ht.offline.borlette.dao.repositories.TicketDetailsRepository;
import com.ht.offline.borlette.dao.repositories.TicketRepository;
import com.ht.offline.borlette.models.Agent;
import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.models.Ticket;
import com.ht.offline.borlette.models.TicketDetails;
import org.apache.logging.log4j .LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("ticketServiceDAO")
public class TicketServiceDAOImpl implements TicketServiceDAO {

    final Logger log = LogManager.getLogger(TicketServiceDAOImpl.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketDetailsRepository ticketDetailsRepository;
    
    @Autowired
    private LotteryScheduleRepository lotteryScheduleRepository;  
    
    @Autowired
    private AgentServiceDAO agentServiceDAO;    

    //persist a ticket
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Ticket persistTicket(Ticket ticket) {
        log.info("<>--------- inside persistTicket(Ticket ticket) ---------<>");

        try {
            log.info("<>--------- Saving a ticket...");   
            //set lottery schedule into ticket
            LotterySchedule lotterySchedule = this.lotteryScheduleRepository
				            				.findById(ticket.getLotterySchedule()
				            				.getLotteryScheduleId()).orElse(null);
            ticket.setLotterySchedule(lotterySchedule);
            
            //set agent into ticket
            Agent agent = agentServiceDAO.loadAgent(ticket.getAgent().getAgentId());
            ticket.setAgent(agent);
            
            //Get ticket details.
            List<TicketDetails> ticketDetails = ticket.getTicketDetails();
            //remove ticket details from ticket
            ticket.setTicketDetails(null);
            
            //persist ticket
            ticket = ticketRepository.save(ticket);
            
            final Ticket tcket = ticket;
            
            //persist ticket details
            ticketDetails.forEach((ticketDetail) -> { 
            	ticketDetail.setTicket(tcket);
            	ticketDetailsRepository.save(ticketDetail);
            });            

            log.info("<>--------- Ticket was saved successfully.");
        } 
        catch (Exception ex) {
            log.warn("?????? Unable to persist the object Ticket :  persistTicket(Ticket ticket)", ex);
        }

        log.info("<>--------- Exit persistTicket(Ticket ticket) ---------<>");
        return ticket;
    }
    
    //persist tickets
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean persistTicket(List<Ticket> tickets) {
        log.info("<>--------- inside persistTicket(List<Ticket> tickets) ---------<>");
        boolean persisted = false;
        try {
            log.info("<>--------- Saving tickets...");            
            //Same lottery schedule for all tickets. So, get the first one.
            LotterySchedule lotterySchedule = this.lotteryScheduleRepository
				            				.findById(tickets.get(0).getLotterySchedule()
				            				.getLotteryScheduleId()).orElse(null);
            
            //Same agent for all tickets. So, get the first one.
            Agent agent = agentServiceDAO.loadAgent(tickets.get(0).getAgent().getAgentId());
            
            List<TicketDetails> ticketDetails = null;
            
            //int iteration = 0;
            
            for(Ticket ticket: tickets) {
            	//Set lottery schedule into ticket. 
	            ticket.setLotterySchedule(lotterySchedule);
	            
	            //set agent into ticket
	            ticket.setAgent(agent);
	            
	            //Get ticket details.
	            ticketDetails = ticket.getTicketDetails();
	            //remove ticket details from ticket
	            ticket.setTicketDetails(null);
	            
	            //persist ticket
	            ticket = ticketRepository.save(ticket);
	            
	            final Ticket tcket = ticket;
	            
	            //persist ticket details
	            ticketDetails.forEach((ticketDetail) -> { 
	            	ticketDetail.setTicket(tcket);
	            	ticketDetailsRepository.save(ticketDetail);
	            });  
	            
	            //iteration++;
            }

            /*persisted = iteration == tickets.size();
            if(!persisted) 
            	log.info("<>--------- Tickets were saved successfully.");
            else 
            	log.info("<>--------- Faild to save tickets' list...");*/
            persisted = true;
        } 
        catch (Exception ex) {
            log.warn("?????? Unable to persist the tickets :  persistTicket(List<Ticket> tickets)", ex);
        }

        log.info("<>--------- Exit persistTicket(List<Ticket> tickets) ---------<>");
        return persisted;
    }    

    //load ticket 
    public Ticket loadTicket(long ticketId) {
        log.info("<>--------- inside loadTicket(long ticketId) ---------<>");
        return this.ticketRepository.findById(ticketId).orElse(null);
    }
    
    //Find all tickets
    public List<Ticket> getTickets(long lotteryScheduleId, Date date) {
        log.info("<>--------- inside getTickets(...) ---------<>");   
        return this.ticketRepository.findAll(lotteryScheduleId, date);
    }    
        
    //Find all tickets of an agent
    public List<Ticket> getTickets(long agentId, long lotteryScheduleId, Date date) {
        log.info("<>--------- inside getTickets(...) ---------<>");   
        return this.ticketRepository.findAll(agentId, lotteryScheduleId, date);
    }
}
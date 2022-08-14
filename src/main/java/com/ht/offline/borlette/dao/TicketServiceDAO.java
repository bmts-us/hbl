/**
 * TicketServiceDAO.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import java.util.Date;
import java.util.List;

import com.ht.offline.borlette.models.Ticket;

public interface TicketServiceDAO {
   Ticket persistTicket(Ticket ticket);
   boolean persistTicket(List<Ticket> tickets);
   Ticket loadTicket(long ticketId);
   List<Ticket> getTickets(long lotteryScheduleId, Date date);
   List<Ticket> getTickets(long agentId, long lotteryScheduleId, Date date);
}
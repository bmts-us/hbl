/**
 * TicketService.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dtos.TicketDTO;
import com.ht.offline.borlette.dtos.TicketWrapperDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TicketService extends Serializable {
  Map<String, Object> persistTicket(TicketDTO ticket);
  boolean persistTicket(List<TicketDTO> ticketsList);
  boolean persistPayment(long ticketId);
  TicketDTO loadTicket(long ticketId);  
  List<TicketDTO> fetchTickets(long lotteryScheduleId, Date date);
  List<TicketDTO> getTickets(long agentId, long lotteryScheduleId, Date date);
  List<TicketWrapperDTO> getTickets(long lotteryScheduleId, Date date);
}

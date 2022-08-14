/**
 * TicketController.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.controllers;

import com.ht.offline.borlette.dtos.LotteryDrawDTO;
import com.ht.offline.borlette.dtos.TicketDTO;
import com.ht.offline.borlette.dtos.TicketWrapperDTO;
import com.ht.offline.borlette.services.LotteryService;
import com.ht.offline.borlette.services.TicketService;
import com.ht.offline.borlette.utils.Utils;

import biz.isman.util.ConvertUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/ticket")
public class TicketController {
    public static final Logger log = LogManager.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private LotteryService lotteryService;    

    @PostMapping(path = "/post")
    public ResponseEntity<Map<String, Object>> post(@RequestBody TicketDTO ticketDTO) {
        log.info("<>------------- Inside post ticket...");

        Map<String, Object> ticketMap = null;

        try {          
        	ticketDTO.setDate(ConvertUtils.getDate(ticketDTO.getTicketDate()));
            ticketMap = ticketService.persistTicket(ticketDTO);
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while posting a ticket :: " + e);
        }

        return ResponseEntity.ok(ticketMap);
    }
    
    @PostMapping(path = "/post/manifest")
    public ResponseEntity<Boolean> postManifest(@RequestBody List<TicketDTO> tickets) {
        log.info("<>------------- Inside post manifest...");
        boolean posted = false;

        try {                  	          	  
        	  //log.info("<>------------- Tickets manifest :: "+tickets);
        	  posted = ticketService.persistTicket(tickets);
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while posting tickets manifest :: " + e);
        }

        return ResponseEntity.ok(posted);
    } 
    
    @GetMapping(path = "/payment/{ticketId}")
    public ResponseEntity<Boolean> payment(@PathVariable long ticketId) {
        log.info("<>------------- Inside payment...");
        boolean paid = false;

        try {                  	          	  
        	paid = ticketService.persistPayment(ticketId);
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while paying a ticket :: " + e);
        }

        return ResponseEntity.ok(paid);
    }     

    @GetMapping("/display/{lotteryScheduleId}/{date}")
    public ResponseEntity<List<TicketWrapperDTO>> getTickets(@PathVariable long lotteryScheduleId, 
    												  @PathVariable String date) {
        log.info("<>--------- Inside getTickets(...) ---------<>");
        return ResponseEntity.ok(ticketService.getTickets(lotteryScheduleId, ConvertUtils.getDate(date)));
    }  
    
    @GetMapping("/get/manifest/{agentId}/{lotteryScheduleId}/{date}")
    public ResponseEntity<Map<String, Object>> getTicketsManifest(@PathVariable long agentId, 
    														  @PathVariable long lotteryScheduleId,
    												  		  @PathVariable String date) {
        log.info("<>--------- Inside getTicketsManifest(...) ---------<> ");
        Map<String, Object> ticketsMap = new HashMap<>();
        List<TicketDTO> tickets = ticketService.getTickets(agentId, lotteryScheduleId, ConvertUtils.getDate(date));
        LotteryDrawDTO loadLotteryDraw = lotteryService.loadLotteryDraw(lotteryScheduleId, ConvertUtils.getDate(date));
        if(Utils.isNotNull(loadLotteryDraw)) {
        	ticketsMap.put("status", "drawn");
        }
        else if(Utils.isNotNull(tickets)){
        	ticketsMap.put("status", "submitted");
        }
        else {
        	ticketsMap.put("status", "");
        }
        
        ticketsMap.put("tickets", tickets);
        return ResponseEntity.ok(ticketsMap);
    }      
}
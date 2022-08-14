/**
 * LotteryServiceDAOImpl.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import com.ht.offline.borlette.dao.repositories.LotteryDrawRepository;
import com.ht.offline.borlette.dao.repositories.LotteryScheduleRepository;
import com.ht.offline.borlette.models.LotteryDraw;
import com.ht.offline.borlette.models.LotterySchedule;
import com.ht.offline.borlette.models.Ticket;
import com.ht.offline.borlette.utils.Utils;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("lotteryServiceDAO")
public class LotteryServiceDAOImpl implements LotteryServiceDAO {

    final Logger log = LogManager.getLogger(LotteryServiceDAOImpl.class);

    @Autowired
    private LotteryDrawRepository lotteryDrawRepository;
    
    @Autowired
    private LotteryScheduleRepository lotteryScheduleRepository;   
        
    @Autowired
    private TicketServiceDAO ticketServiceDAO;   
    
    //persist lotteryDraw
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LotteryDraw persistLotteryDraw(LotteryDraw lotteryDraw, List<Ticket> ticketsWon) {
        log.info("<>--------- inside persistLotteryDraw(LotteryDraw lotteryDraw, List<Ticket> ticketsWon) ---------<>");

        try {
            log.info("<>--------- Saving lotteryDraw.");
            //set lottery schedule into lotteryDraw
            LotterySchedule lotterySchedule = this.lotteryScheduleRepository
				            				.findById(lotteryDraw.getLotterySchedule()
				            				.getLotteryScheduleId()).orElse(null);
            lotteryDraw.setLotterySchedule(lotterySchedule);
            //persist lotteryDraw
            lotteryDraw = lotteryDrawRepository.save(lotteryDraw);
            //persist tickets won
            if(lotteryDraw.getLotteryDrawId() > 0) {
            	ticketServiceDAO.persistTicket(ticketsWon);            	
            }
            	
            log.info("<>--------- LotteryDraw was saved successfully.");
        } 
        catch (Exception ex) {
            log.warn("?????? Unable to persist the object LotteryDraw :  persistLotteryDraw(LotteryDraw lotteryDraw, List<Ticket> ticketsWon)", ex);
        }

        log.info("<>--------- Exit persistLotteryDraw(LotteryDraw lotteryDraw, List<Ticket> ticketsWon) ---------<>");
        return lotteryDraw;
    }

    //load lotteryDraw 
    public LotteryDraw loadLotteryDraw(long lotteryScheduleId, Date date) {
        log.info("<>--------- inside loadLotteryDraw(...) ---------<>");
        return this.lotteryDrawRepository.loadLotteryDraw(lotteryScheduleId, date);        
    }

    //reverse lotteryDraw 
    public void reverseLotteryDraw(long lotteryScheduleId, Date date) {
        log.info("<>--------- inside reverLotteryDraw(...) ---------<>");
        LotteryDraw lotteryDraw = loadLotteryDraw(lotteryScheduleId, date);
        if(Utils.isNull(lotteryDraw))
        	return;
        else			
        	lotteryDrawRepository.delete(lotteryDraw);
    }    
}
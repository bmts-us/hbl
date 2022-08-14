/**
 * LotteryServiceImpl.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dao.LotteryServiceDAO;
import com.ht.offline.borlette.dao.TicketServiceDAO;
import com.ht.offline.borlette.dtos.LotteryDrawDTO;
import com.ht.offline.borlette.dtos.SettingsDTO;
import com.ht.offline.borlette.dtos.StampDTO;
import com.ht.offline.borlette.dtos.TicketDTO;
import com.ht.offline.borlette.dtos.TicketDetailsDTO;
import com.ht.offline.borlette.dtos.TicketWrapperDTO;
import com.ht.offline.borlette.models.LotteryDraw;
import com.ht.offline.borlette.models.Ticket;
import com.ht.offline.borlette.utils.Utils;

import biz.isman.util.ConvertUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("lotteryDrawService")
public class LotteryServiceImpl implements LotteryService {
    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(LotteryServiceImpl.class);

    @Autowired
    protected LotteryServiceDAO lotteryServiceDAO;

    @Autowired
    protected TicketServiceDAO ticketServiceDAO;
    
    @Autowired
    protected SettingsService settingsService;
    
    @Autowired
    protected CommonService commonService;    

    @Autowired
    protected TicketService ticketService; 
    
    //persist lotteryDrawDTO
    public Map<String, Object> persistLotteryDraw(LotteryDrawDTO lotteryDrawDTO) {
        log.info("<>--------- inside persistLotteryDraw(LotteryDrawDTO lotteryDrawDTO) ---------<>");

        Map<String, Object> persistMap = new HashMap<>();

        try {
        	
			if(Utils.isNull(lotteryDrawDTO)) {
				log.info("<>--------- error.code.002 :: LotteryDraw cannot be null.");
				persistMap.put("message", "error.code.002");
				return persistMap;										
			}
			
			//must be always null since it can be deleted at reverse
			LotteryDrawDTO lotterydrawDTO = this.loadLotteryDraw(lotteryDrawDTO.getLotteryScheduleId(), lotteryDrawDTO.getDate());
			if(Utils.isNotNull(lotterydrawDTO)) {
				log.info("<>--------- error.code.004 :: Lottery schedule for this date has been drawn already.");
				persistMap.put("message", "error.code.004");
				return persistMap;								
			}
			
			//validate entries
			if(lotteryDrawDTO.getCashThree().length()!=3) {
				persistMap.put("message", "error.code.003"); //Incorrect cash three
				return persistMap;
			}
			
			if(lotteryDrawDTO.getPlayFour().length()!=4) {
				persistMap.put("message", "error.code.00"); //Incorrect play four
				return persistMap;
			}
			
			//create lottery draw stamp
			StampDTO stampDTO = this.lotteryDrawStamp(lotterydrawDTO, "daltonh");
			lotteryDrawDTO.setStamp(stampDTO);			
            
            //parse lotteryDrawDTO into lotteryDraw
        	LotteryDraw lotteryDraw = LotteryDrawDTO.parse().apply(lotteryDrawDTO);
        	
        	//get tickets won
        	List<Ticket> ticketsWon = this.getTicketsWon(lotteryDrawDTO);        	

            //persist lotteryDraw into the database
            long lotteryDrawId = lotteryServiceDAO.persistLotteryDraw(lotteryDraw, ticketsWon).getLotteryDrawId();

            if(lotteryDrawId > 0) { 
            	lotteryDrawDTO.setLotteryDrawId(lotteryDrawId);            	
                persistMap.put("message", "message.code.007");                
                persistMap.put("lotteryDrawPersisted", lotteryDrawDTO);
            } 
            else persistMap.put("message", "error.code.011");
        } 
        catch (Exception ex) {
            persistMap.put("message", "error.code.011");
            log.error("???????????? Unable to persist the object LotteryDraw :  persistLotteryDraw(LotteryDrawDTO lotteryDrawDTO)", ex);
        }

        return persistMap;
    }

    //method to load lotteryDraw from the database
    public LotteryDrawDTO loadLotteryDraw(long lotteryScheduleId, Date date) {
        log.info("<>--------- inside loadLotteryDraw(...) ---------<>");        
        LotteryDraw lotteryDraw = this.lotteryServiceDAO.loadLotteryDraw(lotteryScheduleId, date);
        if(Utils.isNotNull(lotteryDraw))
            return LotteryDraw.parse().apply(lotteryDraw);
        else 
        	return null;
    }
        
	//function to compute lottery draw 
	private String computeDrawing(LotteryDrawDTO lotteryDraw, String bet, String type, boolean boxAllowed) {		
		String first = null, second = null, third = null;
		String lotto3 = null, lotto4 = null;
		//String lotto5_1 = null, lotto5_2 = null, lotto7 = null; 

		first = lotteryDraw.getCashThree().substring(1); //get the last two characters as first lot.
		second = lotteryDraw.getPlayFour().substring(0, 2); //get the first two numbers from play four which is 4 digits.
		third = lotteryDraw.getPlayFour().substring(lotteryDraw.getPlayFour().length() - 2); //get the last two numbers from play four which is 4 digits.
			
		lotto3 = lotteryDraw.getCashThree(); //lotto 3 is cash three
		lotto4 = lotteryDraw.getPlayFour(); //compute lotto 4			
		//lotto5_1 = lotto3.concat(second); //compute first possibility of lotto 5
		//lotto5_2 = lotto3.concat(third); //compute second possibility of lotto 5
		//lotto7 = lotteryDraw.getCashThree().concat(lotteryDraw.getPlayFour());

		//compute marriage
		String[] marriage = {
						first.concat(" X ").concat(second),
						second.concat(" X ").concat(first),
						first.concat(" X ").concat(third),
						third.concat(" X ").concat(first),
						second.concat(" X ").concat(third),	
						third.concat(" X ").concat(second)
					};
		
		String result = null;
		
		//look for bet
		if(ConvertUtils.isSame(bet, first) && ConvertUtils.isSame(type, "lt.sp")) 
			result = "1";
		else if(ConvertUtils.isSame(bet, second) && ConvertUtils.isSame(type, "lt.sp")) 	
			result = "2";
		else if(ConvertUtils.isSame(bet, third) && ConvertUtils.isSame(type, "lt.sp")) 	
			result = "3";
		else if(ConvertUtils.isSame(bet, lotto3) && ConvertUtils.isSame(type, "lt.l3")) 	
			result = "lt.l3";
		else if(ConvertUtils.isSame(bet, lotto4) && ConvertUtils.isSame(type, "lt.l4")) 	
			result = "lt.l4";
		/*else if((ConvertUtils.isSame(bet, lotto5_1) || ConvertUtils.isSame(bet, lotto5_2)) 
				&& ConvertUtils.isSame(type, "lt.l5")) //lotto 5 is cash three and second or third	
			result = "lt.l5";
		if(ConvertUtils.isSame(bet, lotto7) && ConvertUtils.isSame(type, "lt.l7")) 	
			result = "lt.l7";*/
		else if(ConvertUtils.isSame(type, "lt.ma") || ConvertUtils.isSame(type, "lt.fm")){ 
			for(String mariage: marriage) {
				if(ConvertUtils.isSame(bet, mariage)) {  	
					result = "lt.ma";
					break;
				}
			}
		}		
		
		//compute box
		if(boxAllowed && ConvertUtils.isBlank(result) 
					  && (bet.length()==3 || bet.length()==4 && !(bet.contains("X") 
							|| bet.contains(" ")))) { //|| bet.length()==5 || (bet.length()==7 

			String[] bets = null;
			switch(bet.length()) {
					case 3: 
						if(ConvertUtils.isSame(type, "lt.l3b")) {
							bets = Utils.permute(lotto3);				
							for(int i=0; i < bets.length; i++) {
								if(ConvertUtils.isSame(bet, bets[i])) {
									result = "lt.l3b";
									break;					
								}
							}
						}
						
						break; 
					case 4: 
						if(ConvertUtils.isSame(type, "lt.l4b")) {
							bets = Utils.permute(lotto4);				
							for(int i=0; i < bets.length; i++) {
								if(ConvertUtils.isSame(bet, bets[i])) {
									result = "lt.l4b";
									break;					
								}
							}
						}
						
						break;
					/*case 5: 
						if(ConvertUtils.isSame(type, "lt.l5b")) {
							bets = Utils.permute(lotto5_1);				
							for(int i=0; i < bets.length; i++) {
								if(ConvertUtils.isSame(bet, bets[i])) {
									result = "lt.l5b";
									break;					
								}
							}
							
							//compute box of second possibility of lotto 5
							if(ConvertUtils.isBlank(result)) {
								bets = Utils.permute(lotto5_2);				
								for(int i=0; i < bets.length; i++) {
									if(ConvertUtils.isSame(bet, bets[i])) {
										result = "lt.l5b";
										break;					
									}
								}								
							}
						}
						
						break;
					case 7: 
						if(ConvertUtils.isSame(type, "lt.l7b")) {
							bets = Utils.permute(lotto7);				
							for(int i=0; i < bets.length; i++) {
								if(ConvertUtils.isSame(bet, bets[i])) {
									result = "lt.l7b";
									break;					
								}
							}
						}
						
						break; */
			}
		}		
		
		return result;	
	}	
	
	//Get tickets won
	private List<Ticket> getTicketsWon(LotteryDrawDTO lotteryDraw) {
		log.info("<>--------- inside getTicketsWon(LotteryDraw lotteryDraw)");
		List<Ticket> ticketsWon = new ArrayList<Ticket>(); //hold only the ticket won and be available to persist.
		try {			
			String[] wages = null;
			double amountWon = 0.00;			
			
			Ticket ticket = null;
			int location = 0;
			int wonCounter = 0; //count ticket details won
			
			SettingsDTO settings = settingsService.loadSettings();
			
			List<TicketWrapperDTO> ticketsWrapper = 
									ticketService.getTickets(lotteryDraw.getLotteryScheduleId(), 
															 lotteryDraw.getDate());
			
			for(TicketWrapperDTO ticketWrapper: ticketsWrapper) {
				for(TicketDTO ticketDTO: ticketWrapper.getTickets()) {
					location = 0;
					wonCounter = 0;
					for(TicketDetailsDTO ticketDetailsDTO: ticketDTO.getTicketDetails()) { //step through the ticket details to find bet won
						String result = computeDrawing(lotteryDraw, 
										ticketDetailsDTO.getBetOn(), 
										ticketDetailsDTO.getType(), 
										ticketWrapper.getLotterySchedule().isBoxAllowed());
						
						if(ConvertUtils.isNotBlank(result)) {							
							ticketDetailsDTO.setWon(true);
							wonCounter++;
							amountWon = 0.00;
										
							if(ConvertUtils.isSame(result, "1") 
								|| ConvertUtils.isSame(result, "2")
								|| ConvertUtils.isSame(result, "3")) {
								boolean done = false;									
									
								wages = Utils.split(settings.getLotteryWageSimple(), ',');
										
								for(int i = 0; i < wages.length; i++) {
									if(ConvertUtils.isSame(result, Utils.firstIndexOfString(wages[i], ':'))) {												
										ticketDetailsDTO.setRank(Integer.parseInt(Utils.firstIndexOfString(wages[i], ':')));
										amountWon = ticketDetailsDTO.getAmount() * Integer.parseInt(Utils.lastIndexOfString(wages[i], ':'));											
										ticketDetailsDTO.setAmountWon(amountWon);
										done = true;
										break;												
									}
								}										
									
								if(done) break;
							} 
							else //calculate the amount won for marriage and straight lotto
								if(!settings.isLottoBoxWageDoubled()) {
										if(ConvertUtils.isSame(result, "lt.ma"))
											amountWon = ticketDetailsDTO.getAmount() * settings.getLotteryWageMarriage(); 
										else if(ConvertUtils.isSame(result, "lt.l3"))
											amountWon = ticketDetailsDTO.getAmount() * settings.getLotteryWageLotto3();
										else if(ConvertUtils.isSame(result, "lt.l4"))
											amountWon = ticketDetailsDTO.getAmount() * settings.getLotteryWageLotto4();
								}
								else { //for lotto box we have to check occurrence of a number from the bet, so we can double the pay.
									char[] bets = ticketDetailsDTO.getBetOn().toCharArray();
									boolean appeared = false;
									for(int i=0; i < bets.length; i++) {
										int count = Utils.countChar(ticketDetailsDTO.getBetOn(), bets[i]);
										if(count==2) {
											appeared = true;
											break;
										}
									}
										
									if(!appeared) {
										if(ConvertUtils.isSame(result, "lt.l3"))
											amountWon = ticketDetailsDTO.getAmount() * settings.getLotteryWageLotto3();											
										else if(ConvertUtils.isSame(result, "lt.l4"))
											amountWon = ticketDetailsDTO.getAmount() * settings.getLotteryWageLotto4();
									}
									else {
										if(ConvertUtils.isSame(result, "lt.l3"))
											amountWon = 2 * ticketDetailsDTO.getAmount() * settings.getLotteryWageLotto3();											
										else if(ConvertUtils.isSame(result, "lt.l4"))
											amountWon = 2 * ticketDetailsDTO.getAmount() * settings.getLotteryWageLotto4();
									}
								}
																		
								ticketDetailsDTO.setAmountWon(amountWon);
								break;																				
							} 					
						
						ticketDTO.getTicketDetails().set(location, ticketDetailsDTO); //update ticket details won into the list.						
						location++;
					}

					//ticket details is not empty mean this ticket has won.
					if(wonCounter > 0) {
						ticketDTO.setWon(true);												
						//parse ticketDTO to ticket
						ticket = TicketDTO.parse().apply(ticketDTO);					
						ticketsWon.add(ticket); //to persist into the database						
					}
				}			
			} //Ticket wrapper	
		} 
		catch (Exception ex) {	
			log.error("???????????? Unable to get tickets won :  getTicketsWon(...)", ex);
		}
		
		log.info("<>--------- exit getTicketsWon(...) ---------<>");
		return ticketsWon;
	}	
	

	public boolean reverseLotteryDraw(long lotteryScheduleId, Date date) {
		log.info("<>--------- inside reverseLotteryDraw(long lotteryScheduleId, Date date) ---------<>");
		boolean reversible = false;
		
		//get the tickets
		List<TicketDTO> ticketsList = ticketService.fetchTickets(lotteryScheduleId, date);
				
		if(Utils.isNull(ticketsList)) {
			return reversible;
		}
		
		List<Ticket> tickets = new ArrayList<>();
		int iteration = 0;
		for(TicketDTO ticketDTO: ticketsList) {
			ticketDTO.setWon(false);
			iteration = 0;
			for(TicketDetailsDTO ticketDetailsDTO: ticketDTO.getTicketDetails()) {
				ticketDetailsDTO.setWon(false);
				ticketDetailsDTO.setRank(0);
				ticketDetailsDTO.setAmountWon(0);
				ticketDTO.getTicketDetails().set(iteration, ticketDetailsDTO);
				iteration++;
			}

			tickets.add(TicketDTO.parse().apply(ticketDTO)); 
		}
		
		//persist tickets
		boolean reversed = ticketServiceDAO.persistTicket(tickets);
		if(reversed) {
			lotteryServiceDAO.reverseLotteryDraw(lotteryScheduleId, date);
		}
			
		return reversed;
	}
	
	//LotteryDraw stamps, @lotteryDrawDTO : this argument value must be come from the db.
	private StampDTO lotteryDrawStamp(LotteryDrawDTO lotteryDrawDTO, String connectedUser) {									
		Map<String, Object> lotteryDrawStamp = new HashMap<String, Object>();
			
		if(Utils.isNotNull(lotteryDrawDTO)) {
			lotteryDrawStamp.put("key", lotteryDrawDTO.getLotteryDrawId());	
			if(Utils.isNotNull(lotteryDrawDTO.getStamp())) {
				lotteryDrawStamp.put("createdDate", lotteryDrawDTO.getStamp().getCreatedDate());
				lotteryDrawStamp.put("createdTime", lotteryDrawDTO.getStamp().getCreatedTime());
				lotteryDrawStamp.put("createdBy", lotteryDrawDTO.getStamp().getCreatedBy());	
			} else lotteryDrawStamp.put("key", 0L);
		} else lotteryDrawStamp.put("key", 0L);
			
		lotteryDrawStamp.put("connectedUser", connectedUser);
								
		return commonService.populateStamp(lotteryDrawStamp);
	}    

}
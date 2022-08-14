/**
 * LotteryController.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.controllers;

import com.ht.offline.borlette.dtos.LotteryDrawDTO;
import com.ht.offline.borlette.services.LotteryService;

import biz.isman.util.ConvertUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/lottery")
public class LotteryController {
    public static final Logger log = LogManager.getLogger(LotteryController.class);

    @Autowired
    private LotteryService lotteryService;

    @PostMapping(path = "/draw")
    public ResponseEntity<Map<String, Object>> draw(@RequestBody LotteryDrawDTO lotteryDrawDTO) {
        log.info("<>------------- Inside draw...");

        Map<String, Object> lotteryDrawMap = null;

        try {  
        	lotteryDrawDTO.setDate(ConvertUtils.getDate(lotteryDrawDTO.getDrawDate()));
            lotteryDrawMap = lotteryService.persistLotteryDraw(lotteryDrawDTO);
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while saving lotteryDraw :: " + e);
        }

        return ResponseEntity.ok(lotteryDrawMap);
    }
    
    @PostMapping(path = "/draw/reverse")
    public ResponseEntity<Boolean> reversedraw(@RequestBody LotteryDrawDTO lotteryDrawDTO) {
        log.info("<>------------- Inside reversedraw(...)");

        boolean reversed = false;

        try {  
        	reversed = lotteryService.reverseLotteryDraw(lotteryDrawDTO.getLotteryScheduleId(), 
        				ConvertUtils.getDate(lotteryDrawDTO.getDrawDate()));
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while reversing lotteryDraw :: " + e);
        }

        return ResponseEntity.ok(reversed);
    }    

    @GetMapping("/draw/load/{lotteryScheduleId}/{date}")
    public ResponseEntity<LotteryDrawDTO> loadLotteryDraw(@PathVariable long lotteryScheduleId, 
			  											  @PathVariable String date) {
        log.info("<>------------- Inside loadLotteryDraw(...)");
        return ResponseEntity.ok(lotteryService.loadLotteryDraw(lotteryScheduleId, ConvertUtils.getDate(date)));
    }
    
}
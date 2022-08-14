/**
 * SettingsController.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.controllers;

import com.ht.offline.borlette.dtos.SettingsDTO;
import com.ht.offline.borlette.services.SettingsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/settings")
public class SettingsController {
    public static final Logger log = LogManager.getLogger(SettingsController.class);

    @Autowired
    private SettingsService settingsService;

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, Object>> save(@RequestBody SettingsDTO settingsDTO) {
        log.info("<>------------- Inside save...");
        
        Map<String, Object> settingsMap = null;

        try {            
            settingsMap = settingsService.persistSettings(settingsDTO);
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while saving settings :: " + e);
        }

        return ResponseEntity.ok(settingsMap);
    }

    @GetMapping("/load")
    public ResponseEntity<SettingsDTO> loadSettings() {
        log.info("<>------------- Inside loadSettings()");
        return ResponseEntity.ok(settingsService.loadSettings());
    }
    
    @GetMapping("/find")
    public ResponseEntity<List<SettingsDTO>> findSettings() {
        log.info("<>------------- Inside findSettings()");        
        List<SettingsDTO> settings = new ArrayList<>();
        settings.add(settingsService.loadSettings());
        return ResponseEntity.ok(settings);
    }
    
    //Use for mobile app only
    @GetMapping("/load/asjson")
    public ResponseEntity<Map<String, String>> getSettings() {
        log.info("<>--------- Inside getSettings() ---------<>");
        Map<String, String> settingsMap = new HashMap<String, String>();
        settingsMap.put("settings", settingsService.getSettingsAsJSon().toString());
        return ResponseEntity.ok(settingsMap);
    }    
}
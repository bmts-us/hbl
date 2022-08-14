/**
 * AgentController.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.controllers;

import com.ht.offline.borlette.dtos.AgentDTO;
import com.ht.offline.borlette.services.AgentService;
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
@RequestMapping(path = "/agent")
public class AgentController {
    public static final Logger log = LogManager.getLogger(AgentController.class);

    @Autowired
    private AgentService agentService;

    @PostMapping(path = "/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody AgentDTO agentDTO) {
        log.info("<>------------- Inside save...");

        Map<String, Object> agentMap = null;

        try {            
            agentMap = agentService.persistAgent(agentDTO);
        } 
        catch (Exception e) {
            log.warn("<>------------- Exception occurs while saving agent :: " + e);
        }

        return ResponseEntity.ok(agentMap);
    }

    @GetMapping("/lookup/byId/{agentId}")
    public ResponseEntity<AgentDTO> loadAgent(@PathVariable long agentId) {
        log.info("<>------------- Inside loadAgent()");
        return ResponseEntity.ok(agentService.loadAgent(agentId));
    }
    
    @GetMapping("/all/active")
    public ResponseEntity<List<AgentDTO>> getAgents() {
        log.info("<>--------- Inside getAgents() ---------<>");
        return ResponseEntity.ok(agentService.getAgents());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<AgentDTO>> getAllAgents() {
        log.info("<>--------- Inside getAgents() ---------<>");
        return ResponseEntity.ok(agentService.getAllAgents());
    }
    
    //Use for mobile app only
    @GetMapping("/active/agents")
    public ResponseEntity<Map<String, String>> getActiveAgents() {
        log.info("<>--------- Inside getActiveAgents() ---------<>");
        Map<String, String> agentMap = new HashMap<String, String>();
        agentMap.put("agents", agentService.getAgentsAsJSon().toString());
        return ResponseEntity.ok(agentMap);
    }  

}
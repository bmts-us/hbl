/**
 * AgentServiceImpl.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dao.AgentServiceDAO;
import com.ht.offline.borlette.dtos.AgentDTO;
import com.ht.offline.borlette.models.Agent;
import com.ht.offline.borlette.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("agentService")
public class AgentServiceImpl implements AgentService {
    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(AgentServiceImpl.class);

    @Autowired
    protected AgentServiceDAO agentServiceDAO;

    //persist agentDTO
    public Map<String, Object> persistAgent(AgentDTO agentDTO) {
        log.info("<>--------- inside persistAgent(AgentDTO agentDTO) ---------<>");

        Map<String, Object> persistMap = new HashMap<>();

        try {
        	
        	//load agent from the db
            Agent agent = agentServiceDAO.loadAgent(agentDTO.getAgentId());

            if(Utils.isNotNull(agent)) {
            	agentDTO.setAgentId(agent.getAgentId());
            }
            
            //parse agentDTO into agent
            agent = AgentDTO.parse().apply(agentDTO);

            //persist agent into the database
            long agentId = agentServiceDAO.persistAgent(agent).getAgentId();

            if(agentId > 0) {
                persistMap.put("message", "message.code.007");
                agentDTO.setAgentId(agentId);
                persistMap.put("agentPersisted", agentDTO);
            } 
            else persistMap.put("message", "error.code.011");
        } 
        catch (Exception ex) {
            persistMap.put("message", "error.code.011");
            log.error("???????????? Unable to persist the object Agent :  persistAgent(AgentDTO agentDTO)", ex);
        }

        return persistMap;
    }

    //method to load agent from the database
    public AgentDTO loadAgent(long agentId) {
        log.info("<>--------- inside loadAgent(long agentI) ---------<>");        
        Agent agent = this.agentServiceDAO.loadAgent(agentId);
        if(Utils.isNotNull(agent))
            return Agent.parse().apply(agent);
        else 
        	return null;
    }

    //Get active agents only
    public List<AgentDTO> getAgents() {
        log.info("<>--------- inside getAgents()");
        List<AgentDTO> agents = null;
        try {
            List<Agent> agentsList = agentServiceDAO.getAgents();

            if(Utils.isNotNull(agentsList)) {
                agents = new ArrayList<>();
                AgentDTO agentDTO;
                for(Agent agent: agentsList) {
                	if(agent.isActivated()) {
                		agentDTO = Agent.parse().apply(agent);
                		agents.add(agentDTO);
                	}
                }
            }

        } 
        catch (Exception ex) {
            log.warn("???????????? Unable to get agents' list. ", ex);
        }

        log.info("<>--------- Exit getAgents()");
        return agents;
    }    
    
    //Get all agents
    public List<AgentDTO> getAllAgents() {
        log.info("<>--------- inside getAgents()");
        List<AgentDTO> agents = null;
        try {
            List<Agent> agentsList = agentServiceDAO.getAgents();

            if(Utils.isNotNull(agentsList)) {
                agents = new ArrayList<>();
                AgentDTO agentDTO;
                for(Agent agent: agentsList) {
                	agentDTO = Agent.parse().apply(agent);
                	agents.add(agentDTO);                	
                }
            }

        } 
        catch (Exception ex) {
            log.warn("???????????? Unable to get agents' list. ", ex);
        }

        log.info("<>--------- Exit getAgents()");
        return agents;
    }     
    
    //Get all agents as Json
    public JSONArray getAgentsAsJSon() {
        log.info("<>--------- inside getAgentsAsJSon()");
        JSONArray agentsArray = null; 
        try {
            List<Agent> agentsList = agentServiceDAO.getAgents();
            if(Utils.isNotNull(agentsList)) {
            	agentsArray = new JSONArray(); 
                JSONObject agentObject;
                for(Agent agent: agentsList) {
                	agentObject = AgentDTO.toJson().apply(agent);
                	agentsArray.put(agentObject);                	
                }
            }
        } 
        catch (Exception ex) {
            log.warn("???????????? Unable to get agents' list. ", ex);
        }

        log.info("<>--------- Exit getAgentsAsJSon()");
        return agentsArray;
    }    
    
}
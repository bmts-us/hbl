/**
 * AgentServiceDAOImpl.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import com.ht.offline.borlette.dao.repositories.AgentRepository;
import com.ht.offline.borlette.models.Agent;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("agentServiceDAO")
public class AgentServiceDAOImpl implements AgentServiceDAO {

    final Logger log = LogManager.getLogger(AgentServiceDAOImpl.class);

    @Autowired
    private AgentRepository agentRepository;

    //persist agent
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Agent persistAgent(Agent agent) {
        log.info("<>--------- inside persistAgent(Agent agent) ---------<>");

        try {
            log.info("<>--------- Saving agent.");
            //persist agent
            agent = agentRepository.save(agent);
            log.info("<>--------- Agent was saved successfully.");
        } 
        catch (Exception ex) {
            log.warn("?????? Unable to persist the object Agent :  persistAgent(Agent agent)", ex);
        }

        log.info("<>--------- Exit persistAgent(Agent agent) ---------<>");
        return agent;
    }

    //load agent 
    public Agent loadAgent(long agentId) {
        log.info("<>--------- inside loadAgent(long agentId) ---------<>");
        return this.agentRepository.findById(agentId).orElse(null);
    }
    
    //Find all agents
    public List<Agent> getAgents() {
        log.info("<>--------- inside getAgents() ---------<>");
        return this.agentRepository.findAll();
    }    

}
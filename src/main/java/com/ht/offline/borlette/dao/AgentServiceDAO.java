/**
 * AgentServiceDAO.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao;

import java.util.List;

import com.ht.offline.borlette.models.Agent;

public interface AgentServiceDAO {
   Agent persistAgent(Agent agent);
   Agent loadAgent(long agentId);
   List<Agent> getAgents();
}
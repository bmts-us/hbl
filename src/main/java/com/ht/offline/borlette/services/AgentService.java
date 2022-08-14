/**
 * AgentService.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dtos.AgentDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public interface AgentService extends Serializable {
  Map<String, Object> persistAgent(AgentDTO agent);
  AgentDTO loadAgent(long agentId);
  List<AgentDTO> getAgents();
  List<AgentDTO> getAllAgents();
  JSONArray getAgentsAsJSon();
}

/**
 * AgentDTO.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import org.json.JSONObject;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ht.offline.borlette.models.Agent;

import biz.isman.util.ConvertUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("agentId")
	private long agentId;
	
	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("lastname")
	private String lastname;
	
	@JsonProperty("middlename")
	private String middlename;
	
	@JsonProperty("fullName")
	private String fullName;	
	
	@JsonProperty("gender")
	private String gender;			
	
	@JsonProperty("activated")
	private boolean activated;

	@JsonProperty("nationalId")
	private String nationalId;

	@JsonProperty("address")
	private String address;
	
	@JsonProperty("telephone")
	private String telephone;
	
	@JsonProperty("localisation")
	private String localisation;

	private List<LotteryScheduleDTO> lotterySchedules;

    private StampDTO stamp;
	
	/**
	 * @return the fullName
	 */	
	public String fullName() {
        StringBuffer buff = new StringBuffer();
        buff.append(ConvertUtils.isBlank(firstname) ? "" : firstname);
        buff.append(buff.length() > 1 ? " " : "");
        buff.append(ConvertUtils.isBlank(middlename) ? "" : middlename);
        buff.append(buff.length() > 1 ? " " : "");
        buff.append(ConvertUtils.isBlank(lastname) ? "" : lastname.toUpperCase());
        this.fullName = buff.toString();
        return buff.length() > 1 ? fullName : "";
	}
    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a AgentDTO to Agent
     * @return - function that will convert a AgentDTO to Agent
     *-------------------------------------------------------------------------*/
    public static Function<AgentDTO, Agent> parse() {
        return agentDTO -> {
            Assert.notNull(agentDTO, "AgentDTO cannot be null.");
            Agent agent = new Agent();
            agent.setAgentId(agentDTO.getAgentId());
            agent.setFirstname(agentDTO.getFirstname());
            agent.setLastname(agentDTO.getLastname());
            agent.setMiddlename(agentDTO.getMiddlename());
            agent.setGender(agentDTO.getGender());
            agent.setActivated(agentDTO.isActivated());
            agent.setNationalId(agentDTO.getNationalId());
            agent.setAddress(agentDTO.getAddress());
            agent.setTelephone(agentDTO.getTelephone());
            agent.setLocalisation(agentDTO.getLocalisation());            
            return agent;
        };
    }
    
    public static Function<Agent, JSONObject> toJson() {
        return agent -> {
            Assert.notNull(agent, "AgentDTO cannot be null.");
            JSONObject agentObject = new JSONObject();
            agentObject.put("id", agent.getAgentId());
            agentObject.put("firstName", agent.getFirstname());
            agentObject.put("lastName", agent.getLastname());
            agentObject.put("middleName", agent.getMiddlename());
            
            return agentObject;
        };
    }    

}
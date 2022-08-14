/**
 * Agent.java
 * Created on 2022-07-26
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.models;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.Assert;

import com.ht.offline.borlette.dtos.AgentDTO;
import com.ht.offline.borlette.dtos.TicketDTO;

import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.function.Function;


/**
 * The persistent class for the agents database table.
 * 
 */ 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema="adminsys")
public class Agent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="agent_id", unique=true, nullable=false)
	private long agentId;
	
	private String firstname;

	private String lastname;
	
	private String middlename;
	
	private String gender;			
	
	@Column(name="is_activated")
	private boolean activated;

	@Column(name="national_id")
	private String nationalId;

	private String address;
	
	private String telephone;
	
	private String localisation;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Time createdTime;

	@Temporal(TemporalType.DATE)
	@Column(name="date_stamp")
	private Date dateStamp;

	@Column(name="time_stamp")
	private Time timeStamp;

	@Column(name="user_stamp")
	private String userStamp;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="agent", cascade = { CascadeType.ALL })
	private List<Ticket> tickets;

    /*-------------------------------------------------------------------------*
     * Creates a function that will convert a Agent to AgentDTO
     * @return - function that will convert a Agent to AgentDTO
     *-------------------------------------------------------------------------*/
    public static Function<Agent, AgentDTO> parse() {
        return agent -> {
            Assert.notNull(agent, "Agent cannot be null.");
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentId(agent.getAgentId());
            agentDTO.setFirstname(agent.getFirstname());
            agentDTO.setLastname(agent.getLastname());
            agentDTO.setMiddlename(agent.getMiddlename());
            agentDTO.setGender(agent.getGender());
            agentDTO.setActivated(agent.isActivated());
            agentDTO.setNationalId(agent.getNationalId());
            agentDTO.setAddress(agent.getAddress());
            agentDTO.setTelephone(agent.getTelephone());
            agentDTO.setLocalisation(agent.getLocalisation());            
            return agentDTO;
        };
    }
}
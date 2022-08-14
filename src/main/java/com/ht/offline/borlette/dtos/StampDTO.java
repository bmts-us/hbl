/**
 * StampDTO.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dtos;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StampDTO   implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String createdBy;
	
	private String userStamp;
	
	private Date createdDate;
	
	private Date dateStamp;	
	
	private String createdOn; //to display created date
	
	private String stampOn;	//to display date stamp
	
	private Time createdTime;
	
	private Time timeStamp;
	
	private String createdAt; //to display created time
	
	private String stampAt; //to display time stamp

	private String date;


}

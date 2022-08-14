/**
 * CommonServiceImpl.java
 * Created on 2022-07-28
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ht.offline.borlette.dtos.StampDTO;
import com.ht.offline.borlette.utils.DateUtils;
import com.ht.offline.borlette.utils.Utils;

import biz.isman.util.ConvertUtils;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	//stamps handler
	public StampDTO populateStamp(Map<String, Object> createdStamp) {
		StampDTO stampDTO = new StampDTO();
	      
		long key = (Long)createdStamp.get("key");
	    
	    if(key<=0) { //false or 0 means create only 
	    	stampDTO.setCreatedDate(DateUtils.dateNow());
	    	stampDTO.setCreatedTime(DateUtils.currentTime());
	    	stampDTO.setCreatedBy((String)createdStamp.get("connectedUser"));
	    } else {
	    	Date createdDate = (Date)createdStamp.get("createdDate");
	    	Time createdTime = (Time)createdStamp.get("createdTime");
	    	String createdBy = (String)createdStamp.get("createdBy");
	    	stampDTO.setCreatedDate(Utils.isNotNull(createdDate) ? createdDate : DateUtils.dateNow());
	    	stampDTO.setCreatedTime(Utils.isNotNull(createdTime) ? createdTime : DateUtils.currentTime());
	    	stampDTO.setCreatedBy(ConvertUtils.isNotBlank(createdBy) ? createdBy : (String)createdStamp.get("connectedUser"));	    	
	    }
		
	    //for create and update
	    stampDTO.setDateStamp(DateUtils.dateNow());		
	    stampDTO.setTimeStamp(DateUtils.currentTime());				
	    stampDTO.setUserStamp((String)createdStamp.get("connectedUser"));
				
		return stampDTO;
	}	
}

/**
 * CommonService.java
 * Created on 2022-07-28
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dtos.StampDTO;

public interface CommonService {

	StampDTO populateStamp(java.util.Map<String, Object> createdStamp);
}

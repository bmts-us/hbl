/**
 * SettingsService.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.services;

import com.ht.offline.borlette.dtos.SettingsDTO;
import java.io.Serializable;
import java.util.Map;

import org.json.JSONObject;

public interface SettingsService extends Serializable {
  Map<String, Object> persistSettings(SettingsDTO settings);
  SettingsDTO loadSettings();
  JSONObject getSettingsAsJSon();
}

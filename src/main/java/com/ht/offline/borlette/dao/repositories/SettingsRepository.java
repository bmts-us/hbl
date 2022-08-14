/**
 * SettingsRepository.java
 * Created on 2022-07-25
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.dao.repositories;

import com.ht.offline.borlette.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long>{
    // Find settings
    @Query("SELECT s FROM Settings s")
    Settings loadSettings();	
}


package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    Configuration findConfigurationById(Integer id);
    Optional<Configuration> findConfigurationByMachineId(Integer machineId);

    @Transactional
    @Modifying
    int deleteByMachineId(Integer machineId);

    @Transactional
    @Modifying
    @Query("UPDATE tblConfiguration m SET m.pump1 = ?1, m.pump1Volume = ?2 WHERE m.machineId = ?3")
    void updatePump1(Integer ingredientId, Integer volume, Integer machineId);
    @Transactional
    @Modifying
    @Query("UPDATE tblConfiguration m SET m.pump2 = ?1, m.pump2Volume = ?2 WHERE m.machineId = ?3")
    void updatePump2(Integer ingredientId, Integer volume, Integer machineId);
    @Transactional
    @Modifying
    @Query("UPDATE tblConfiguration m SET m.pump3 = ?1, m.pump3Volume = ?2 WHERE m.machineId = ?3")
    void updatePump3(Integer ingredientId, Integer volume, Integer machineId);
    @Transactional
    @Modifying
    @Query("UPDATE tblConfiguration m SET m.pump4 = ?1, m.pump4Volume = ?2 WHERE m.machineId = ?3")
    void updatePump4(Integer ingredientId, Integer volume, Integer machineId);
}

package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.processes.PumpConfiguration;
import com.pjatk.barkeeper.barbackend.repositories.ConfigurationRepository;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PumpService {
    private final PumpConfiguration pumpConfiguration;
    private final ConfigurationRepository configurationRepository;
    private final MachineRepository machineRepository;

    public Optional<Configuration> updateIngredientForPump(int pumpNumber, int ingredientId, int volume, String username, String serialNumber) {
        pumpConfiguration.addIngredient(pumpNumber, ingredientId, volume, username, serialNumber);
        int machineId = machineRepository.findBySerialNumber(serialNumber).get().getId();
        return configurationRepository.findConfigurationByMachineId(machineId);
    }

    public boolean deleteConfigurationForMachine(String username, String serialNumber) {
        return pumpConfiguration.cleanPumpsConfiguration(username, serialNumber);
    }
}

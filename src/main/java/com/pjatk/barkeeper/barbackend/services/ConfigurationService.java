package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.processes.MachineConfiguration;
import com.pjatk.barkeeper.barbackend.repositories.ConfigurationRepository;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;
    private final MachineConfiguration machineConfiguration;
    private final MachineRepository machineRepository;

    public Configuration getConfigurationById(Integer id){
        return configurationRepository.findConfigurationById(id);
    }

    public Optional<Configuration> getConfigurationBySerialNumber(String serialNumber){
        int machineId = machineRepository.findBySerialNumber(serialNumber).get().getId();
        return configurationRepository.findConfigurationByMachineId(machineId);
    }

    public Machine cleanMachine (String machineSerialNumber, String userName) {
        return machineConfiguration.cleanMachine(machineSerialNumber, userName);
    }

    public int getNextCleaning(String serialNumber) {
        LocalDate nextCleaning = machineRepository.findBySerialNumber(serialNumber).get().getNextCleaning().toLocalDate();
        return Period.between(LocalDate.now(), nextCleaning).getDays();
    }
}

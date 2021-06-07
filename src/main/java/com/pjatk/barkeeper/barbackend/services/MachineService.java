package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.processes.MachineConfiguration;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class MachineService {
    private final MachineRepository machineRepository;
    private final MachineConfiguration machineConfiguration;

    public Machine getMachineBySerialNumber(String serialNumber) {
        if (machineRepository.findBySerialNumber(serialNumber).isPresent())
        {
            return machineRepository.findBySerialNumber(serialNumber).get();
        } else {
            throw new IllegalStateException("Machine cannot be found");
        }
    }

    public List<Machine> getMachineByUserId(Integer userId) {
        return machineRepository.findByUserId(userId);
    }

    public Optional<Machine> addNewMachine(String username, String serialNumber) {
                return machineConfiguration.addNewMachine(username, serialNumber);
    }

    public Machine deleteOwner(String username, String serialNumber) {
        return machineConfiguration.deleteOwnerAndMachine(username, serialNumber);
    }
}

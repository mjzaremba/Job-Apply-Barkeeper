package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.UserConnection;
import com.pjatk.barkeeper.barbackend.processes.UserConnectionConfiguration;
import com.pjatk.barkeeper.barbackend.repositories.UserConnectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserConnectionService {
    private final UserConnectionRepository userConnectionRepository;
    private final UserConnectionConfiguration userConnectionConfiguration;

    public Optional<UserConnection> getByMachineUserId(Integer machineUserId) {
        return userConnectionRepository.findByMachineUserId(machineUserId);
    }

    public List<UserConnection> getAllByMachineId(Integer machineId){
        return userConnectionRepository.findAllByMachineId(machineId);
    }

    public UserConnection connectUserToMachine(String username, String serialNumber) {
        return userConnectionConfiguration.connectWithMachine(username, serialNumber);
    }

    public Machine deleteAllMachineConnections(String username, String serialNumber) {
        return userConnectionConfiguration.deleteAllMachineConnections(username, serialNumber);
    }
}

package com.pjatk.barkeeper.barbackend.processes;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.UserConnection;
import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.models.user.UserGroup;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import com.pjatk.barkeeper.barbackend.repositories.UserConnectionRepository;
import com.pjatk.barkeeper.barbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserConnectionConfiguration {
    private final UserConnectionRepository userConnectionRepository;
    private final UserRepository userRepository;
    private final MachineRepository machineRepository;

    public UserConnection connectWithMachine(String username, String serialNumber) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new IllegalStateException("User does not exist.");

        Optional<Machine> machine = machineRepository.findBySerialNumber(serialNumber);
        if (machine.isEmpty())
            throw new IllegalStateException("Machine does not exist.");

        Optional<UserConnection> userConnection = userConnectionRepository.findByMachineUserId(user.get().getId());

        //TODO hardcoded values
        if (userConnection.isPresent()) {
            userConnectionRepository.delete(userConnection.get());
        }

        userConnectionRepository.save(new UserConnection(machine.get().getId(), user.get().getId(),
                0, LocalDateTime.now().plusHours(24)));

        return userConnectionRepository.findByMachineUserId(user.get().getId()).get();
    }

    public Machine deleteAllMachineConnections(String username, String serialNumber) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new IllegalStateException("User does not exist.");
        if (!user.get().getUserGroup().name().equalsIgnoreCase(UserGroup.OWNER.name()))
            throw new IllegalStateException("Only owner is able to clean all connections");

        Optional<Machine> machine = machineRepository.findBySerialNumber(serialNumber);
        if (machine.isEmpty())
            throw new IllegalStateException("Machine does not exists");
        if (machine.get().getUserId() != user.get().getId())
            throw new IllegalStateException("Only owner is able to clean all connections");

        userConnectionRepository.deleteByMachineId(machine.get().getId());
        return machine.get();
    }
}

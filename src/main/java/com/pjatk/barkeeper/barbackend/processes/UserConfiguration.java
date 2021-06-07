package com.pjatk.barkeeper.barbackend.processes;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.models.user.UserGroup;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import com.pjatk.barkeeper.barbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserConfiguration {
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;

    public User setUserAsOwner(String username, String serialNumber) {
        Optional<Machine> machine = machineRepository.findBySerialNumber(serialNumber);
        if (machine.isEmpty()) {
            throw new IllegalStateException("Cannot find machine in database.");
        } else if (machine.get().getUserId() != 0) {
            throw new IllegalStateException("Machine has an owner. Please unlink current owner.");
        }

        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty())
            throw new IllegalStateException("User do not exists.");

        if (user.get().getUserGroup().name().equalsIgnoreCase(UserGroup.USER.name()))
            userRepository.updateUserGroup(user.get().getId(), UserGroup.OWNER);

        machineRepository.updateMachineOwner(user.get().getId(), serialNumber);
        return user.get();
    }
}

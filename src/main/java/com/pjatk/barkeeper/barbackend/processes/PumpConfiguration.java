package com.pjatk.barkeeper.barbackend.processes;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.models.user.UserGroup;
import com.pjatk.barkeeper.barbackend.repositories.ConfigurationRepository;
import com.pjatk.barkeeper.barbackend.repositories.IngredientRepository;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import com.pjatk.barkeeper.barbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PumpConfiguration {
    private final UserRepository userRepository;
    private final ConfigurationRepository configurationRepository;
    private final MachineRepository machineRepository;
    private final IngredientRepository ingredientRepository;

    public Optional<Configuration> addIngredient(int pumpNumber, int ingredientId, int volume, String username, String serialNumber) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new IllegalStateException("User does not exist.");
        if (!user.get().getUserGroup().name().equalsIgnoreCase(UserGroup.OWNER.name()))
            throw new IllegalStateException("Only owner is able to configure pumps.");

        Optional<Machine> machine = machineRepository.findBySerialNumber(serialNumber);
        if (machine.isEmpty())
            throw new IllegalStateException("Machine does not exist.");
        if (machine.get().getUserId() != user.get().getId())
            throw new IllegalStateException("Only owner is able to configure pumps.");

        if (!ingredientRepository.existsById(ingredientId)) {
            throw new IllegalStateException("Please add valid Ingredient");
        }
        //TODO hardcoded values and too long constructor
        Optional<Configuration> configuration = configurationRepository
                .findConfigurationByMachineId(machine.get().getId());
        if (configuration.isEmpty()) {
            configurationRepository.save(new Configuration(0, 0, 0, 0, 0, 0, 0, 0, machine.get().getId()));
        }

        switch (pumpNumber) {
            case 1:
                configurationRepository.updatePump1(ingredientId, volume, machine.get().getId());
                break;
            case 2:
                configurationRepository.updatePump2(ingredientId, volume, machine.get().getId());
                break;
            case 3:
                configurationRepository.updatePump3(ingredientId, volume, machine.get().getId());
                break;
            case 4:
                configurationRepository.updatePump4(ingredientId, volume, machine.get().getId());
                break;
            default:
                throw new IllegalStateException("Please pick pump from 1 to 4");
        }
        return configurationRepository.findConfigurationByMachineId(machine.get().getId());
    }

    public boolean cleanPumpsConfiguration(String username, String serialNumber) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new IllegalStateException("User does not exist.");
        if (!user.get().getUserGroup().name().equalsIgnoreCase(UserGroup.OWNER.name()))
            throw new IllegalStateException("Only owner is able to configure pumps.");

        Optional<Machine> machine = machineRepository.findBySerialNumber(serialNumber);
        if (machine.isEmpty())
            throw new IllegalStateException("Machine does not exist.");
        if (machine.get().getUserId() != user.get().getId())
            throw new IllegalStateException("Only user is able to delete configuration.");

        Optional<Configuration> configuration = configurationRepository.findConfigurationByMachineId(machine.get().getId());
        configuration.ifPresent(configurationRepository::delete);

        return true;
    }
}

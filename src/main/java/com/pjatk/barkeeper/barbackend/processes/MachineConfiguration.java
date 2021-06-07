package com.pjatk.barkeeper.barbackend.processes;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.order.PreparingStatus;
import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.models.user.UserGroup;
import com.pjatk.barkeeper.barbackend.mqtt.BarkeeperConnector;
import com.pjatk.barkeeper.barbackend.repositories.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class MachineConfiguration {

    private final MachineRepository machineRepository;
    private final BarkeeperConnector barkeeperConnector;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserConnectionRepository userConnectionRepository;
    private final ConfigurationRepository configurationRepository;
    private final MachineTopDrinksRepository machineTopDrinksRepository;

    @SneakyThrows
    public Machine cleanMachine (String serialNumber, String userName) {
        MqttClient barkeeperClient = null;
        String isMachineSwitchedOn = "";
        int connectionCounter = 0;

        Optional<User> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            throw new IllegalStateException("User does not exists.");
        }

        if (user.get().getUserGroup().name().equalsIgnoreCase("USER")) {
            throw new IllegalStateException("Only owner of machine with serial number: " + serialNumber +
                    "is able to start cleaning process.");
        }

        Optional<Machine> currentMachine = machineRepository
                .findBySerialNumber(serialNumber);

        if (currentMachine.isEmpty())
            throw new IllegalStateException("Machine with serial number " + serialNumber + " does not exist.");

        currentMachine.get().setLastCleaning(LocalDateTime.now());
        currentMachine.get().setNextCleaning(LocalDateTime.now().plusDays(14));
        //TODO hardcoded value
        if (!orderRepository.findAllByMachineIdAndPreparingStatusInOrderByOrderDate(currentMachine.get().getId(),
                List.of(PreparingStatus.PREPARING, PreparingStatus.AWAITING)).isEmpty()) {
            throw new IllegalStateException("Cleaning process cannot started if any drink is preparing.");
        }

        try {
            barkeeperClient = barkeeperConnector.makeConnection();
            barkeeperConnector.sendMessageToBarkeeper(barkeeperClient, "sd-112485-6665");
            TimeUnit.SECONDS.sleep(2);
            isMachineSwitchedOn = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
        } catch (MqttException e) {
            throw new IllegalStateException("Cannot connect to machine");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new IllegalStateException("Cannot get message from machine");
        }

        while (isMachineSwitchedOn.isEmpty() || !isMachineSwitchedOn.equalsIgnoreCase("true")) {
            TimeUnit.SECONDS.sleep(5);
            if (connectionCounter >= 3)
                throw new IllegalStateException("Machine Barkeeper is switched off");
            isMachineSwitchedOn = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
            connectionCounter++;
        }
        //TODO code duplication

        //TODO hardcoded value
        barkeeperConnector.sendMessageToBarkeeper(barkeeperClient, "CLEAN");
        String barkeeperReceive = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");

        TimeUnit.SECONDS.sleep(3);
        while (!barkeeperReceive.equalsIgnoreCase("done")) {
            barkeeperReceive = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
        }
        barkeeperClient.disconnect();

        return currentMachine.get();
    }

    public Machine deleteOwnerAndMachine(String username, String serialNumber) {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty())
            throw new IllegalStateException("User does not exist.");
        Optional<Machine> machine = machineRepository.findByUserIdAndSerialNumber(user.get().getId(), serialNumber);
        if (machine.isEmpty())
            throw new IllegalStateException("Machine does not exists.");

        orderRepository.deleteByMachineId(machine.get().getId());
        configurationRepository.deleteByMachineId(machine.get().getId());
        machineTopDrinksRepository.deleteByMachineId(machine.get().getId());
        userConnectionRepository.deleteByMachineId(machine.get().getId());
        machineRepository.delete(machine.get());

        userRepository.updateUserGroup(user.get().getId(), UserGroup.USER);

        return machine.get();
    }

    public Optional<Machine> addNewMachine (String username, String serialNumber) {
        if (machineRepository.findBySerialNumber(serialNumber).isPresent())
            throw new IllegalStateException("Machine currently exist in database.");

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new IllegalStateException("User does not exist.");

        machineRepository.save(new Machine(serialNumber, user.get().getId(), null, LocalDateTime.now().plusDays(14)));
        userRepository.updateUserGroup(user.get().getId(), UserGroup.OWNER);
        return machineRepository.findBySerialNumber(serialNumber);
    }
}

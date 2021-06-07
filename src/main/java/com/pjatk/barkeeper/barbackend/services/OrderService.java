package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.models.IngredientToDrink;
import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.order.Order;
import com.pjatk.barkeeper.barbackend.models.order.PreparingStatus;
import com.pjatk.barkeeper.barbackend.mqtt.BarkeeperConnector;
import com.pjatk.barkeeper.barbackend.repositories.ConfigurationRepository;
import com.pjatk.barkeeper.barbackend.repositories.IngredientToDrinkRepository;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import com.pjatk.barkeeper.barbackend.repositories.OrderRepository;
import com.pjatk.barkeeper.barbackend.processes.ordering.OrderValidation;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequestScope
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BarkeeperConnector barkeeperConnector;
    private final MachineRepository machineRepository;
    private final MachineTopDrinksService machineTopDrinksService;
    private final ConfigurationRepository configurationRepository;
    private final UserValidation userValidation;
    private final OrderValidation orderValidation;
    private final IngredientToDrinkRepository ingredientToDrinkRepository;

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order addOrderToQueue(Order userOrder) {
        if (!userValidation.checkUserConnection(userOrder)) {
            throw new IllegalStateException("User is not logging into app or do not have connection with machine");
        }

        userOrder.setPreparingStatus(PreparingStatus.AWAITING);
        orderRepository.save(userOrder);

        return userOrder;
    }

    public List<Order> getOrdersQueue(String serialNumber) {
        int machineId = machineRepository.findBySerialNumber(serialNumber).get().getId();
        return orderRepository.findAllByMachineIdAndPreparingStatusInOrderByOrderDate(machineId,
                List.of(PreparingStatus.AWAITING, PreparingStatus.PREPARING));
    }

    public Order updateAsRefused(Order order) {
        Optional<Order> currentOrder = orderRepository.findOrderByUserIdAndOrderDateAndPreparingStatus(order.getUserId(), order.getOrderDate(), PreparingStatus.AWAITING);
        if(currentOrder.isEmpty()) {
            throw new IllegalStateException("Cannot find order");
        }
        orderRepository.updatePreparingStatus(PreparingStatus.REFUSED, currentOrder.get().getId());
        return order;
    }

    @SneakyThrows
    public Order makeDrink(String serialNumber) {
        MqttClient barkeeperClient = null;
        String isMachineSwitchedOn = "";
        int connectionCounter = 0;

        try {
            barkeeperClient = barkeeperConnector.makeConnection();
            barkeeperConnector.sendMessageToBarkeeper(barkeeperClient, "sd-112485-6665");
            TimeUnit.SECONDS.sleep(2);
            isMachineSwitchedOn = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
        } catch (MqttException e) {
            barkeeperClient.disconnect();
            throw new IllegalStateException("Cannot connect to machine");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            barkeeperClient.disconnect();
            throw new IllegalStateException("Cannot get message from machine");
        }

        while (isMachineSwitchedOn.isEmpty() || !isMachineSwitchedOn.equalsIgnoreCase("true")) {
            TimeUnit.SECONDS.sleep(5);
            if (connectionCounter >= 3) {
                barkeeperClient.disconnect();
                throw new IllegalStateException("Machine Barkeeper is switched off");
            }
            isMachineSwitchedOn = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
            connectionCounter++;
        }

        Optional<Machine> currentMachine = machineRepository.findBySerialNumber(serialNumber);

        //TODO catch nullpointer
        Optional<Order> currentOrderPerMachine = orderRepository
                .findTopByPreparingStatusAndMachineIdOrderByOrderDate(PreparingStatus.AWAITING,
                        currentMachine.get().getId());
        if(currentOrderPerMachine.isEmpty()) {
            barkeeperClient.disconnect();
            throw new IllegalStateException("No orders in machine");
        }


        orderRepository.updatePreparingStatus(PreparingStatus.PREPARING, currentOrderPerMachine.get().getId());

        if (!checkIngredientsAndsetVolumeAvailability(ingredientToDrinkRepository
                .findByDrinkId(currentOrderPerMachine.get().getDrinkId()), currentMachine.get().getId())) {
            barkeeperClient.disconnect();
            throw new IllegalStateException("Not enough ingredient for making picked drink.");
        }

        String drinkMessage = orderValidation.validateOrder(currentOrderPerMachine.get());

        try {
            barkeeperConnector.sendMessageToBarkeeper(barkeeperClient, drinkMessage);
        } catch (MqttException e) {
            barkeeperClient.disconnect();
            throw new IllegalStateException("Problem with connection to Machine");
        }
        String barkeeperReceive = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
        while (!barkeeperReceive.equalsIgnoreCase(PreparingStatus.DONE.name())) {
            barkeeperReceive = barkeeperConnector.getBarkeeperMessage().get("sd-112485-6665");
        }
        orderRepository.updatePreparingStatus(PreparingStatus.DONE, currentOrderPerMachine.get().getId());
        machineTopDrinksService.updateDrinkOrderCount(currentOrderPerMachine.get());
        barkeeperClient.disconnect();

        return currentOrderPerMachine.get();
    }

    private boolean checkIngredientsAndsetVolumeAvailability(List<IngredientToDrink> ingredients, Integer machineId) {
        Optional<Configuration> currentConfiguration = configurationRepository.findConfigurationByMachineId(machineId);
        if (currentConfiguration.isEmpty())
            throw new IllegalStateException("Lack of ingredient configuration.");

        boolean checkIfIngredientsExist = false;
        for (IngredientToDrink ingredient : ingredients) {
            for (int pumpNumber = 1; pumpNumber <= 4; pumpNumber++) {
                switch (pumpNumber) {
                    case 1:
                        if (currentConfiguration.get().getPump1() == ingredient.getIngredientsId())
                            if (currentConfiguration.get().getPump1Volume() >= ingredient.getVolume()) {
                                currentConfiguration.get().setPump1Volume(
                                        currentConfiguration.get().getPump1Volume() - ingredient.getVolume()
                                );
                                checkIfIngredientsExist = true;
                            }
                        break;
                    case 2:
                        if (currentConfiguration.get().getPump2() == ingredient.getIngredientsId())
                            if (currentConfiguration.get().getPump2Volume() >= ingredient.getVolume()) {
                                currentConfiguration.get().setPump2Volume(
                                        currentConfiguration.get().getPump2Volume() - ingredient.getVolume()
                                );
                                checkIfIngredientsExist = true;
                            }
                        break;
                    case 3:
                        if (currentConfiguration.get().getPump3() == ingredient.getIngredientsId())
                            if (currentConfiguration.get().getPump3Volume() >= ingredient.getVolume()) {
                                currentConfiguration.get().setPump3Volume(
                                        currentConfiguration.get().getPump3Volume() - ingredient.getVolume()
                                );
                                checkIfIngredientsExist = true;
                            }
                        break;
                    case 4:
                        if (currentConfiguration.get().getPump4() == ingredient.getIngredientsId())
                            if (currentConfiguration.get().getPump4Volume() >= ingredient.getVolume()) {
                                currentConfiguration.get().setPump4Volume(
                                        currentConfiguration.get().getPump4Volume() - ingredient.getVolume()
                                );
                                checkIfIngredientsExist = true;
                            }
                        break;
                    default:
                        checkIfIngredientsExist = false;
                        break;
                }
                if (checkIfIngredientsExist)
                    break;
            }
            if (!checkIfIngredientsExist)
                throw new IllegalStateException("Lack of ingredient for preparing picked drink.");
        }
        return checkIfIngredientsExist;
    }
}

package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.MachineTopDrinks;
import com.pjatk.barkeeper.barbackend.models.order.Order;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import com.pjatk.barkeeper.barbackend.repositories.MachineTopDrinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineTopDrinksService {
    public final MachineTopDrinksRepository machineTopDrinksRepository;
    public final MachineRepository machineRepository;

    @Autowired
    public MachineTopDrinksService(MachineTopDrinksRepository machineTopDrinksRepository, MachineRepository machineRepository) {
        this.machineTopDrinksRepository = machineTopDrinksRepository;
        this.machineRepository = machineRepository;
    }

    public List<MachineTopDrinks> getAllMachineTopDrinksBySerialNumber(String serialNumber) {
        int machineId = machineRepository.findBySerialNumber(serialNumber).get().getId();
        return machineTopDrinksRepository.findAllByMachineId(machineId);
    }

    public List<MachineTopDrinks> getTopMachineTopDrinksBySerialNumber(String serialNumber) {
        int machineId = machineRepository.findBySerialNumber(serialNumber).get().getId();
        return machineTopDrinksRepository.findTop3ByMachineIdOrderByDrinkOrderCountDesc(machineId);
    }

    public void updateDrinkOrderCount(Order order){
        int orderDrinkIdId = order.getDrinkId();
        int orderMachineId = order.getMachineId();
        if(!machineTopDrinksRepository.existsByDrinkIdAndMachineId(orderDrinkIdId, orderMachineId))
        {
            machineTopDrinksRepository.save(new MachineTopDrinks(orderMachineId, orderDrinkIdId, 1));
        }
        else {
            MachineTopDrinks drink = machineTopDrinksRepository.findMachineTopDrinksByDrinkIdAndMachineId(orderDrinkIdId, orderMachineId);
            int drinkOrderCount = drink.getDrinkOrderCount() + 1;
            machineTopDrinksRepository.updateMachineTopDrink(drinkOrderCount, orderDrinkIdId, orderMachineId);
        }
    }
}

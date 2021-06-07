package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.MachineTopDrinks;
import com.pjatk.barkeeper.barbackend.services.MachineTopDrinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "machine-top-drinks")
public class MachineTopDrinksController {
    private final MachineTopDrinksService machineTopDrinksService;

    @Autowired
    public MachineTopDrinksController(MachineTopDrinksService machineTopDrinksService) {
        this.machineTopDrinksService = machineTopDrinksService;
    }

    @GetMapping(value = "/serial-number/{serialNumber}")
    public List<MachineTopDrinks> getMachineTopDrinksBySerialNumber(@PathVariable String serialNumber) {
        return machineTopDrinksService.getAllMachineTopDrinksBySerialNumber(serialNumber);
    }

    @GetMapping(value = "/top/{serialNumber}")
    public List<MachineTopDrinks> getTopMachineTopDrinksBySerialNumber(@PathVariable String serialNumber) {
        return machineTopDrinksService.getTopMachineTopDrinksBySerialNumber(serialNumber);
    }
}

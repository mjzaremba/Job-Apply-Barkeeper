package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.services.MachineService;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "machine")
public class MachineController {
    private final MachineService machineService;
    private final UserValidation userValidation;

    @GetMapping(value = "serial-number/{serialNumber}")
    public Machine getMachineBySerialNumber(@PathVariable String serialNumber) {
        return machineService.getMachineBySerialNumber(serialNumber);
    }

    @GetMapping(value = "/user-id/{userId}")
    public List<Machine> getMachineByUserId(@PathVariable Integer userId) {
        return machineService.getMachineByUserId(userId);
    }

    @PostMapping(value = "/add/{serialNumber}")
    public Optional<Machine> addNewMachine(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return machineService.addNewMachine(username, serialNumber);
    }

    @DeleteMapping(value = "/delete-owner/{serialNumber}")
    public Machine deleteOwner(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return machineService.deleteOwner(username, serialNumber);
    }
}

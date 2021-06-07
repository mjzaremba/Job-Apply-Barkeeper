package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.models.UserConnection;
import com.pjatk.barkeeper.barbackend.services.UserConnectionService;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "user-connection")
public class UserConnectionController {
    private final UserConnectionService userConnectionService;
    private final UserValidation userValidation;

    @Autowired
    public UserConnectionController(UserConnectionService userConnectionService, UserValidation userValidation) {
        this.userConnectionService = userConnectionService;
        this.userValidation = userValidation;
    }

    @GetMapping(value = "/user-id/{userId}")
    public Optional<UserConnection> getUserConnectionByMachineUserId(@PathVariable Integer userId){
        return userConnectionService.getByMachineUserId(userId);
    }

    @GetMapping(value = "/machine-id/{machineId}")
    public List<UserConnection> getUserConnectionByMachineId(@PathVariable Integer machineId){
        return userConnectionService.getAllByMachineId(machineId);
    }

    @PostMapping(value = "add-new-connection/{serialNumber}")
    public UserConnection addNewConnection(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userConnectionService.connectUserToMachine(username, serialNumber);
    }

    @DeleteMapping(value = "delete-all-connections/{serialNumber}")
    public Machine deleteAllConnections(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userConnectionService.deleteAllMachineConnections(username, serialNumber);
    }
}

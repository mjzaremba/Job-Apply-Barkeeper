package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.models.Machine;
import com.pjatk.barkeeper.barbackend.repositories.MachineRepository;
import com.pjatk.barkeeper.barbackend.services.ConfigurationService;
import com.pjatk.barkeeper.barbackend.services.MachineService;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path="configuration")
public class ConfigurationController {
    private final ConfigurationService configurationService;
    private final UserValidation userValidation;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService, UserValidation userValidation) {
        this.configurationService = configurationService;
        this.userValidation = userValidation;
    }

    @GetMapping(value = "/id/{id}")
    public Configuration getConfigurationById(@PathVariable Integer id){
        return configurationService.getConfigurationById(id);
    }

    @GetMapping(value = "/serial-number/{serialNumber}")
    public Optional<Configuration> getConfigurationBySerialNumber(@PathVariable String serialNumber){
        return configurationService.getConfigurationBySerialNumber(serialNumber);
    }

    @PostMapping(value = "/clean/{serialNumber}")
    public Machine cleanMachine(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return configurationService.cleanMachine(serialNumber, username);
    }

    @GetMapping(value = "/clean/{serialNumber}")
    public int getNextCleaning(@PathVariable String serialNumber) {
        return configurationService.getNextCleaning(serialNumber);
    }
}

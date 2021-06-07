package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.services.PumpService;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping(path = "pump")
@AllArgsConstructor
public class PumpController {
    private final PumpService pumpService;
    private final UserValidation userValidation;

    @PostMapping(value = "/add-ingredient/{pumpNumber}/{ingredientId}/{volume}/{serialNumber}")
    public Optional<Configuration> updateConfigurationForPump(@PathVariable Integer pumpNumber,
                                                              @PathVariable Integer ingredientId,
                                                              @PathVariable Integer volume,
                                                              @PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return pumpService.updateIngredientForPump(pumpNumber, ingredientId, volume, username, serialNumber);
    }

    @DeleteMapping(value = "/delete-configuration/{serialNumber}")
    public boolean deleteConfiguration(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return pumpService.deleteConfigurationForMachine(username, serialNumber);
    }
}

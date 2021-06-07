package com.pjatk.barkeeper.barbackend.processes.ordering;

import com.pjatk.barkeeper.barbackend.models.Configuration;
import com.pjatk.barkeeper.barbackend.models.IngredientToDrink;
import com.pjatk.barkeeper.barbackend.models.order.Order;
import com.pjatk.barkeeper.barbackend.repositories.ConfigurationRepository;
import com.pjatk.barkeeper.barbackend.repositories.IngredientToDrinkRepository;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderValidation {
    @Autowired
    UserValidation userValidation;
    @Autowired
    ConfigurationRepository configurationRepository;
    @Autowired
    IngredientToDrinkRepository ingredientToDrinkRepository;

    public String validateOrder(Order userOrder) {
        StringBuilder message = new StringBuilder();
        if (userValidation.checkUserConnection(userOrder)) {
            Optional<Configuration> configuration = configurationRepository.findConfigurationByMachineId(userOrder.getMachineId());
            List<IngredientToDrink> ingredientsToDrink = ingredientToDrinkRepository.findByDrinkId(userOrder.getDrinkId());

            for (IngredientToDrink ingredient : ingredientsToDrink) {
                int ingredientId = ingredient.getIngredientsId();
                int volume = ingredient.getVolume();

                if (ingredientId == configuration.get().getPump1() && configuration.get().getPump1Volume() >= volume) {
                    message.append("1:");
                } else if (ingredientId == configuration.get().getPump2() && configuration.get().getPump2Volume() >= volume) {
                    message.append("2:");
                } else if (ingredientId == configuration.get().getPump3() && configuration.get().getPump3Volume() >= volume) {
                    message.append("3:");
                } else if (ingredientId == configuration.get().getPump4() && configuration.get().getPump4Volume() >= volume) {
                    message.append("4:");
                }
                message.append(ingredient.getVolume()).append(";");
            }
        }
        return message.toString();
    }

}

package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.IngredientToDrink;
import com.pjatk.barkeeper.barbackend.services.IngredientsToDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "ingredients-to-drinks")
public class IngredientsToDrinksController {
    private final IngredientsToDrinkService ingredientsToDrinkService;

    @Autowired
    public IngredientsToDrinksController(IngredientsToDrinkService ingredientsToDrinkService) {
        this.ingredientsToDrinkService = ingredientsToDrinkService;
    }

    @GetMapping(path = "/all")
    public List<IngredientToDrink> getAllIngredientsToDrinks() {
        return ingredientsToDrinkService.getAllIngredientsToDrinks();
    }
}

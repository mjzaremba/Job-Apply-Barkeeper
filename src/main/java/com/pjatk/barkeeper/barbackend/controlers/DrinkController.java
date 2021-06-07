package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.Drink;
import com.pjatk.barkeeper.barbackend.services.DrinkService;
import com.pjatk.barkeeper.barbackend.services.IngredientsToDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "drink")
public class DrinkController {
    private final DrinkService drinkService;

    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping(path = "/all")
    public List<Drink> getDrinks() {
        return drinkService.getDrinks();
    }

    @GetMapping(value = "/id/{id}")
    public Drink getDrinkById(@PathVariable Integer id) {
        return drinkService.getDrinkById(id);
    }

    @GetMapping(path = "/ingredients")
    public List<Drink> getDrinkByIngredientId(@RequestParam List<Integer> ingredientIds) {
        return drinkService.getDrinksByIngredients(ingredientIds);
    }
}

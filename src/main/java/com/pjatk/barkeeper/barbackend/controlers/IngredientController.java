package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.Ingredient;
import com.pjatk.barkeeper.barbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "/name/{name}")
    public Ingredient getIngredientByName(@PathVariable String name) {
        return ingredientService.getIngredientByName(name);
    }

    @GetMapping(value = "/id/{id}")
    public Ingredient getIngredientById(@PathVariable Integer id) {
        return ingredientService.getIngredientById(id);
    }

    @GetMapping(path = "/all")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }
}

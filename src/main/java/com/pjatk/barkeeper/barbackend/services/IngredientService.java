package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Ingredient;
import com.pjatk.barkeeper.barbackend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findIngredientByName(name);
    }

    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findIngredientById(id);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
}

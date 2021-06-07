package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.IngredientToDrink;
import com.pjatk.barkeeper.barbackend.repositories.IngredientToDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service
@RequestScope
public class IngredientsToDrinkService {
    @Autowired
    IngredientToDrinkRepository ingredientToDrinkRepository;

    public List<IngredientToDrink> getAllIngredientsToDrinks() {
        return ingredientToDrinkRepository.findAll();
    }
}

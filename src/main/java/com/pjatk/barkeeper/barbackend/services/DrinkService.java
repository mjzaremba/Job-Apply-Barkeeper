package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.Drink;
import com.pjatk.barkeeper.barbackend.models.IngredientToDrink;
import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.repositories.DrinkRepository;
import com.pjatk.barkeeper.barbackend.repositories.IngredientToDrinkRepository;
import com.pjatk.barkeeper.barbackend.repositories.SearchHistoryRepository;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Service
@RequestScope
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final IngredientToDrinkRepository ingredientToDrinkRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository, IngredientToDrinkRepository ingredientToDrinkRepository, SearchHistoryRepository searchHistoryRepository) {
        this.drinkRepository = drinkRepository;
        this.ingredientToDrinkRepository = ingredientToDrinkRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    public List<Drink> getDrinks() {
        return drinkRepository.findAll();
    }

    public Drink getDrinkById(Integer id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        searchHistoryRepository.insertSearchHistory(currentUser.getId(), id);
        return drinkRepository.findDrinkById(id);
    }

    public List<Drink> getDrinksByIngredients(List<Integer> ingredientIds) {
        //TODO sneaky code
        List<Integer> drinksWithProperQuantity = ingredientToDrinkRepository
                .findDrinkIdWithIngredientQuantity((long) ingredientIds.size());

        List<IngredientToDrink> ingredientToDrinks = ingredientToDrinkRepository
                .findIngredientToDrinkByDrinkIdIn(drinksWithProperQuantity);

        List<IngredientToDrink> currentAvailableDrinks = new ArrayList<>();
        List<IngredientToDrink> excludedDrinks = new ArrayList<>();
        List<Integer> drinks = new ArrayList<>();

        for (IngredientToDrink ingredientToDrink : ingredientToDrinks) {
            if (ingredientIds.stream().anyMatch(ingredientId -> ingredientId == ingredientToDrink.getIngredientsId())
                    && excludedDrinks.stream().noneMatch( ingredientToDrink1 -> ingredientToDrink1.getDrinkId() == ingredientToDrink.getDrinkId())) {
                currentAvailableDrinks.add(ingredientToDrink);
            }
            if (ingredientIds.stream().noneMatch(ingredientId -> ingredientId == ingredientToDrink.getIngredientsId())) {
                excludedDrinks.add(ingredientToDrink);
                currentAvailableDrinks.removeIf(ingredientToDrink1 -> ingredientToDrink1.getDrinkId() == ingredientToDrink.getDrinkId());
            }
        }


        for (IngredientToDrink ingredientToDrink : currentAvailableDrinks) {
            drinks.add(ingredientToDrink.getDrinkId());
        }

        return drinkRepository.findDrinkByIdIn(drinks);
    }
}

package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tblIngredientsToDrinks")
public class IngredientToDrink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int drinkId;
    private int ingredientsId;
    private int volume;

    public IngredientToDrink(int id, int drinkId, int ingredientsId, int volume) {
        this.id = id;
        this.drinkId = drinkId;
        this.ingredientsId = ingredientsId;
        this.volume = volume;
    }

    public IngredientToDrink() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getIngredientsId() {
        return ingredientsId;
    }

    public void setIngredientsId(int ingredientsId) {
        this.ingredientsId = ingredientsId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}

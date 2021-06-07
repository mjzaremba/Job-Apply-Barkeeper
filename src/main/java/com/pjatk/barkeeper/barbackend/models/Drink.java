package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.*;

@Entity(name = "tblDrinks")
public class Drink {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;
    private String name;
    private int rank;
    private String drinkPicture;

    public Drink(String name, String description, int rank, String drinkPicture) {
        this.name = name;
        this.description = description;
        this.rank = rank;
        this.drinkPicture = drinkPicture;
    }

    public Drink(int id, String name, String description, int rank, String drinkPicture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rank = rank;
        this.drinkPicture = drinkPicture;
    }

    public Drink() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDrinkPicture() {
        return drinkPicture;
    }

    public void setDrinkPicture(String drinkPicture) {
        this.drinkPicture = drinkPicture;
    }
}

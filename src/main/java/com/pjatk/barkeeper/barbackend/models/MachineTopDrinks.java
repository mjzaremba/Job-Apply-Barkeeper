package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tblMachineTopDrinks")
public class MachineTopDrinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int machineId;
    private int drinkId;
    private int drinkOrderCount;

    public MachineTopDrinks(int machineId, int drinkId, int drinkOrderCount) {
        this.machineId = machineId;
        this.drinkId = drinkId;
        this.drinkOrderCount = drinkOrderCount;
    }

    public MachineTopDrinks() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getDrinkOrderCount() {
        return drinkOrderCount;
    }

    public void setDrinkOrderCount(int drinkOrderCount) {
        this.drinkOrderCount = drinkOrderCount;
    }
}

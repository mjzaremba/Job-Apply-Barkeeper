package com.pjatk.barkeeper.barbackend.models.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tblOrders")
public class Order {
    @Id
    private int id;
    private int userId;
    private int drinkId;
    private int machineId;
    @Enumerated(EnumType.STRING)
    private PreparingStatus preparingStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    public Order(int userId, int drinkId, int machineId, PreparingStatus preparingStatus, LocalDateTime orderDate) {
        this.userId = userId;
        this.drinkId = drinkId;
        this.machineId = machineId;
        this.preparingStatus = preparingStatus;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public PreparingStatus getPreparingStatus() {
        return preparingStatus;
    }

    public void setPreparingStatus(PreparingStatus preparingStatus) {
        this.preparingStatus = preparingStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}

package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

@Entity(name = "tblMachine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String serialNumber;
    private int userId;
    private LocalDateTime lastCleaning;
    private LocalDateTime nextCleaning;

    public Machine(int id, String serialNumber, int userId) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.userId = userId;
    }

    public Machine(String serialNumber, int userId, LocalDateTime lastCleaning, LocalDateTime nextCleaning) {
        this.serialNumber = serialNumber;
        this.userId = userId;
        this.lastCleaning = lastCleaning;
        this.nextCleaning = nextCleaning;
    }

    public Machine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getLastCleaning() {
        return lastCleaning;
    }

    public void setLastCleaning(LocalDateTime lastCleaning) {
        this.lastCleaning = lastCleaning;
    }

    public LocalDateTime getNextCleaning() {
        return nextCleaning;
    }

    public void setNextCleaning(LocalDateTime nextCleaning) {
        this.nextCleaning = nextCleaning;
    }
}

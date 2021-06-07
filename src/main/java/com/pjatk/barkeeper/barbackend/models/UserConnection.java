package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tblUserConnection")
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int machineId;
    private int machineUserId;
    private int timeout;
    private LocalDateTime effectiveDate;

    public UserConnection(int machineId, int machineUserId, int timeout, LocalDateTime effectiveDate) {
        this.machineId = machineId;
        this.machineUserId = machineUserId;
        this.timeout = timeout;
        this.effectiveDate = effectiveDate;
    }

    public UserConnection() {
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

    public int getMachineUserId() {
        return machineUserId;
    }

    public void setMachineUserId(int machineUserId) {
        this.machineUserId = machineUserId;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}

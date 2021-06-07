package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tblConfiguration")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int pump1;
    private int pump2;
    private int pump3;
    private int pump4;
    private int pump1Volume;
    private int pump2Volume;
    private int pump3Volume;
    private int pump4Volume;
    private int machineId;

    public Configuration(int pump1, int pump2, int pump3, int pump4, int pump1Volume, int pump2Volume, int pump3Volume, int pump4Volume, int machineId) {
        this.pump1 = pump1;
        this.pump2 = pump2;
        this.pump3 = pump3;
        this.pump4 = pump4;
        this.pump1Volume = pump1Volume;
        this.pump2Volume = pump2Volume;
        this.pump3Volume = pump3Volume;
        this.pump4Volume = pump4Volume;
        this.machineId = machineId;
    }

    public Configuration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPump1() {
        return pump1;
    }

    public void setPump1(int pump1) {
        this.pump1 = pump1;
    }

    public int getPump2() {
        return pump2;
    }

    public void setPump2(int pump2) {
        this.pump2 = pump2;
    }

    public int getPump3() {
        return pump3;
    }

    public void setPump3(int pump3) {
        this.pump3 = pump3;
    }

    public int getPump4() {
        return pump4;
    }

    public void setPump4(int pump4) {
        this.pump4 = pump4;
    }

    public int getPump1Volume() {
        return pump1Volume;
    }

    public void setPump1Volume(int pump1Volume) {
        this.pump1Volume = pump1Volume;
    }

    public int getPump2Volume() {
        return pump2Volume;
    }

    public void setPump2Volume(int pump2Volume) {
        this.pump2Volume = pump2Volume;
    }

    public int getPump3Volume() {
        return pump3Volume;
    }

    public void setPump3Volume(int pump3Volume) {
        this.pump3Volume = pump3Volume;
    }

    public int getPump4Volume() {
        return pump4Volume;
    }

    public void setPump4Volume(int pump4Volume) {
        this.pump4Volume = pump4Volume;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

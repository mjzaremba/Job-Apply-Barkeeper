package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tblSearchHistory")
public class SearchHistory {
    @Id
    private int id;
    private int userId;
    private int drinkId;
    private LocalDateTime searchDate;

    public SearchHistory(int id, int userId, int drinkId, LocalDateTime searchDate) {
        this.id = id;
        this.userId = userId;
        this.drinkId = drinkId;
        this.searchDate = searchDate;
    }

    public SearchHistory() {
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

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public LocalDateTime getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDateTime searchDate) {
        this.searchDate = searchDate;
    }
}

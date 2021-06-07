package com.pjatk.barkeeper.barbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tblUserHasUserConnection")
public class UserHasUserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int userConnectionId;

    public UserHasUserConnection(int id, int userId, int userConnectionId) {
        this.id = id;
        this.userId = userId;
        this.userConnectionId = userConnectionId;
    }

    public UserHasUserConnection() {
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

    public int getUserConnectionId() {
        return userConnectionId;
    }

    public void setUserConnectionId(int userConnectionId) {
        this.userConnectionId = userConnectionId;
    }
}

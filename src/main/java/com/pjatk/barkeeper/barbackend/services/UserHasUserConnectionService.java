package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.UserHasUserConnection;
import com.pjatk.barkeeper.barbackend.repositories.UserHasUserConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHasUserConnectionService {
    private final UserHasUserConnectionRepository userHasUserConnectionRepository;

    @Autowired
    public UserHasUserConnectionService(UserHasUserConnectionRepository userHasUserConnectionRepository) {
        this.userHasUserConnectionRepository = userHasUserConnectionRepository;
    }

    public List<UserHasUserConnection> getUserHasUserConnectionByUserId (Integer userId) {
        return userHasUserConnectionRepository.findByUserId(userId);
    }
}

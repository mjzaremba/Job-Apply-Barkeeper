package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.UserHasUserConnection;
import com.pjatk.barkeeper.barbackend.services.UserHasUserConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user-has-connection")
public class UserHasUserConnectionController {
    private final UserHasUserConnectionService userHasUserConnectionService;

    @Autowired
    public UserHasUserConnectionController(UserHasUserConnectionService userHasUserConnectionService) {
        this.userHasUserConnectionService = userHasUserConnectionService;
    }

    @GetMapping(value = "/user-id/{userId}")
    public List<UserHasUserConnection> getUserHasUserConnectionByUserId(@PathVariable Integer userId) {
        return userHasUserConnectionService.getUserHasUserConnectionByUserId(userId);
    }
}
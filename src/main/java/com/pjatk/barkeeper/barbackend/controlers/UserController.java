package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.models.user.UserRegistrationRequest;
import com.pjatk.barkeeper.barbackend.services.UserService;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService userService;
    private final UserValidation userValidation;

    @Autowired
    public UserController(UserService userService, UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    @GetMapping(path = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/register")
    public String register(@RequestBody UserRegistrationRequest registrationRequest) { return userService.register(registrationRequest); }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam String token) {
        return userService.confirmToken(token);
    }

    @PostMapping(path = "/mark-as-owner/{serialNumber}")
    public User markAsOwner(@PathVariable String serialNumber) {
        String username = userValidation.checkUserExists(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userService.setUserAsOwner(username, serialNumber);
    }
}

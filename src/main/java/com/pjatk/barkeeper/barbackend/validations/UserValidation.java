package com.pjatk.barkeeper.barbackend.validations;

import com.pjatk.barkeeper.barbackend.models.UserConnection;
import com.pjatk.barkeeper.barbackend.models.order.Order;
import com.pjatk.barkeeper.barbackend.models.user.User;
import com.pjatk.barkeeper.barbackend.repositories.UserConnectionRepository;
import com.pjatk.barkeeper.barbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserValidation {
    @Autowired
    private UserConnectionRepository userConnectionRepository;

    public boolean checkUserConnection(Order order) {
        Optional<UserConnection> currentUser = userConnectionRepository
                .findByMachineUserIdAndAndMachineId(order.getUserId(), order.getMachineId());
        return currentUser.isPresent();
    }

    public String checkUserExists(Object currentUser) {
        String username = "";
        if (currentUser instanceof UserDetails)
            username = ((UserDetails) currentUser).getUsername();
        else
            username = currentUser.toString();
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("User does not exists.");
        }
        return username;
    }
}

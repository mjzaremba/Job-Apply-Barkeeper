package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.order.Order;
import com.pjatk.barkeeper.barbackend.services.OrderService;
import com.pjatk.barkeeper.barbackend.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "order")
public class OrderController {
    private final OrderService orderService;
    private final UserValidation userValidation;

    @Autowired
    public OrderController(OrderService orderService, UserValidation userValidation) {
        this.orderService = orderService;
        this.userValidation = userValidation;
    }

    @GetMapping(value = "/user-id/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Integer userId) { return orderService.getOrdersByUserId(userId); }

    @PostMapping(path = "/save")
    public Order saveOrder(@RequestBody Order userOrder) {
        return orderService.addOrderToQueue(userOrder);
    }

    @PostMapping(path = "/mark-as-refused")
    public Order markOrderAsRefused(@RequestBody Order userOrder) {
        return orderService.updateAsRefused(userOrder);
    }

    @PostMapping(path = "/make-drink/{serialNumber}")
    public Order markOrder(@PathVariable String serialNumber) {
        return orderService.makeDrink(serialNumber);
    }

    @GetMapping(value = "/queue/{serialNumber}")
    public List<Order> getOrdersQueueBySerialNumber(@PathVariable String serialNumber) { return orderService.getOrdersQueue(serialNumber); }
}

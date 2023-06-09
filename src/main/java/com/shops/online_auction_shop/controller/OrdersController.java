package com.shops.online_auction_shop.controller;


import com.shops.online_auction_shop.collection.Order;
import com.shops.online_auction_shop.dto.OrderRequest;
import com.shops.online_auction_shop.enums.OrderStatus;
import com.shops.online_auction_shop.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);
    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = {"/list", "/list/{user}"})
    public ResponseEntity<List<Order>> listOrders(@PathVariable Optional<String> user) {
        boolean isAdmin = checkIfAdmin();
        String username = getCurrentUserName();

        if (user.isPresent()) {
            if (isAdmin) {
                LOGGER.info("List {} orders at {} request.", user, username);
                return ResponseEntity.status(HttpStatus.OK).body(orderService.getUserOrders(user.get()));
            }
            LOGGER.warn("Unauthorized order list attempt by {}.", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if (isAdmin) {
            LOGGER.info("List all orders at {} request.", username);
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
        }

        LOGGER.info("List {} orders.", username);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getUserOrders(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeOrder(@Valid @NotBlank @RequestParam String id) {
        String adminName = getCurrentUserName();

        LOGGER.info("Remove {} order at {} request.", id, adminName);

        orderService.removeOrderByOrderId(id);
        return ResponseEntity.ok("Removed");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@Valid @RequestBody OrderRequest orderDetails) {
        String user = getCurrentUserName();

        Order newOrder = prepareOrder(orderDetails);

        LOGGER.info("Adding new order for {}.", user);

        orderService.addNewOrder(newOrder);

        return ResponseEntity.ok("success");
    }

    private boolean checkIfAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority().equals("ROLE_ADMIN");
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Order prepareOrder(OrderRequest details) {
        return Order.builder()
                .customer(getCurrentUserName())
                .status(OrderStatus.WAITING)
                .shippingAddress(details.getShippingAddress())
                .items(details.getItems())
                .build();
    }
}

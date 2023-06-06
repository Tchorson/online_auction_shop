package com.shops.online_auction_shop.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @GetMapping("/list")
    public ResponseEntity<String> listOrders(){
        return ResponseEntity.ok("hi");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeOrder(@RequestParam String user, @RequestParam String orderNumber){

        return ResponseEntity.ok("removed");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder() {
        return ResponseEntity.ok("added");
    }
}

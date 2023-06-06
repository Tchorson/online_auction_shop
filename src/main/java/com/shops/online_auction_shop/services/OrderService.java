package com.shops.online_auction_shop.services;


import com.shops.online_auction_shop.repositories.OrderRepository;
import com.shops.online_auction_shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
}

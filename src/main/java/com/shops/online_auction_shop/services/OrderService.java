package com.shops.online_auction_shop.services;


import com.shops.online_auction_shop.collection.Order;
import com.shops.online_auction_shop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getUserOrders(String customerName) {
        return orderRepository.findOrdersByCustomer(customerName);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void removeOrderByOrderId(String orderId) {
        orderRepository.deleteOrderByOrderId(orderId);
    }

    @Transactional
    public Order addNewOrder(Order order) {
        return orderRepository.save(order);
    }
}

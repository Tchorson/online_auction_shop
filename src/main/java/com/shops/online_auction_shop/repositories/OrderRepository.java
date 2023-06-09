package com.shops.online_auction_shop.repositories;

import com.shops.online_auction_shop.collection.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findOrdersByCustomer(String username);

    void deleteOrderByOrderId(String orderId);
}

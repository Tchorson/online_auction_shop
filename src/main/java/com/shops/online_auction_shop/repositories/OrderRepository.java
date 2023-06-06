package com.shops.online_auction_shop.repositories;

import com.shops.online_auction_shop.collection.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findOrderByCustomer(String username);
    Order findOrderByCustomerOrOrderNo(String username, String email);

    Order deleteOrderByCustomerOrOrderNo(String username);
}

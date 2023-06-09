package com.shops.online_auction_shop.collection;

import com.shops.online_auction_shop.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String orderId;
    private String customer;
    private OrderStatus status;
    private String shippingAddress;
    private ArrayList<Item> items;
}

package com.shops.online_auction_shop.collection;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Item {

    private String name;
    private double price;
    private String currency;
    private int quantity;
}

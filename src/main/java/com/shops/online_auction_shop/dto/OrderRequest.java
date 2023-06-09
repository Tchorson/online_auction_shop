package com.shops.online_auction_shop.dto;

import com.shops.online_auction_shop.collection.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequest {

    private String shippingAddress;
    @NotEmpty
    private ArrayList<Item> items;
}

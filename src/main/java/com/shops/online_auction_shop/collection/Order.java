package com.shops.online_auction_shop.collection;

import com.shops.online_auction_shop.enums.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

import java.util.ArrayList;


@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Order {
    @Id
    private String orderId;
    private int orderNo;
    private String customer;
    private Date creationDate;
    private OrderStatus status;
    private String shippingAddress;

    // order items
    private ArrayList<Item> orders;

    //methods for order items
    public void addItem(Item item){
        orders.add(item);
    };

    public Item getItemToOrders(int index) {
        return orders.get(index);
    }

    public int getOrdersSize(){
        return orders.size();
    }

    public void removeItemFromOrders(int index) {
        orders.remove(index);
    }

    public void clearOrders(){
        orders.clear();
    }

    //order status should be updated as the delivery progresses
    public void updateOrderStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }
}

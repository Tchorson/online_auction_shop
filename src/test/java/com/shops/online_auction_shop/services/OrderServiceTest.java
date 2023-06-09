package com.shops.online_auction_shop.services;

import com.shops.online_auction_shop.collection.Item;
import com.shops.online_auction_shop.collection.Order;
import com.shops.online_auction_shop.enums.OrderStatus;
import com.shops.online_auction_shop.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;
    private Order order;

    private String id;
    private String username;
    private String shippingAddress;
    private String role;
    private Item item;
    private ArrayList<Item> items;
    private OrderStatus status;

    private String itemName;
    private double price;
    private String currency;
    private int quantity;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository);

        id = "1aeb";
        username = "user";
        shippingAddress = "Cracow, Poland";
        role = "ROLE_USER";
        status = OrderStatus.PREPARING;

        itemName = "Winter hat, limited edition";
        price = 25.99;
        currency = "Dollar";
        quantity = 2;
        item = new Item(itemName, price, currency, quantity);

        items = new ArrayList<>();
        items.add(item);

        order = new Order(id, username, status, shippingAddress, items);
    }

    @Test
    void shouldGetUserOrdersTest() {
        //given
        Mockito.when(orderRepository.findOrdersByCustomer(username)).thenReturn(List.of(order));

        //when
        List<Order> resultList = orderService.getUserOrders(username);
        Order result = resultList.get(0);

        //then
        Assertions.assertEquals(result, order);
    }

    @Test
    void shouldGetEmptyListTest() {
        //given
        Mockito.when(orderRepository.findOrdersByCustomer(username)).thenReturn(Collections.emptyList());

        //when
        List<Order> resultList = orderService.getUserOrders(username);

        //then
        Assertions.assertEquals(resultList.size(), 0);
    }

    @Test
    void shouldGetAllOrdersTest() {
        //given
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order));

        //when
        List<Order> resultList = orderService.getAllOrders();
        Order result = resultList.get(0);

        //then
        Assertions.assertEquals(resultList.size(), 1);
        Assertions.assertEquals(result, order);
    }

    @Test
    void shouldRemoveOrderByOrderIdTest() {
        //given
        Mockito.doNothing().when(orderRepository).deleteOrderByOrderId(id);

        //when
        orderService.removeOrderByOrderId(id);

        //then
        Mockito.verify(orderRepository, times(1)).deleteOrderByOrderId(id);
    }

    @Test
    void shouldAddNewOrderTest() {
        //given
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        //when
        Order result = orderService.addNewOrder(order);

        //then
        Assertions.assertEquals(result, order);
    }
}
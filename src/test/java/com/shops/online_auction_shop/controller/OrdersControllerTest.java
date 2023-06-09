package com.shops.online_auction_shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shops.online_auction_shop.collection.Item;
import com.shops.online_auction_shop.collection.Order;
import com.shops.online_auction_shop.dto.OrderRequest;
import com.shops.online_auction_shop.enums.OrderStatus;
import com.shops.online_auction_shop.services.OrderService;
import com.shops.online_auction_shop.services.UserService;
import com.shops.online_auction_shop.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest({OrdersController.class})
class OrdersControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private String username;
    private OrderRequest orderRequest;
    private String id;
    private String shippingAddress;
    private OrderStatus status;
    private final String PATH = "/api/v1/orders";
    private Item item;
    private String itemName;
    private double price;
    private String currency;
    private int quantity;

    private ArrayList<Item> items;
    private Order order;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        shippingAddress = "Test address";

        username = "user";
        id = "1aeb";
        itemName = "Light bulb";
        price = 20.15;
        currency = "PLN";
        quantity = 4;
        status = OrderStatus.DELIVERED;

        item = new Item(itemName, price, currency, quantity);
        items = new ArrayList<>();
        items.add(item);
        orderRequest = new OrderRequest(shippingAddress, items);
        order = buildTestOrder();

        orders = List.of(order);
    }

    @Test
    void shouldForbidAddOrderTest() throws Exception {
        String json = objectMapper.writeValueAsString(orderRequest);

        this.mock.perform(post(PATH + "/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldForbidAnonymousListOrdersTest() throws Exception {
        this.mock.perform(get(PATH + "/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldForbidAnonymousListOrdersForUserTest() throws Exception {
        this.mock.perform(get(PATH + "/list/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldForbidAnonymousRemoveOrderTest() throws Exception {
        this.mock.perform(delete(PATH + "/remove"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldForbidRemoveOrderByUserTest() throws Exception {
        this.mock.perform(delete(PATH + "/remove"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldRemoveOrderByAdminTest() throws Exception {
        this.mock.perform(delete(PATH + "/remove?id=12345"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldThrownExceptionWhenNoIdTest() throws Exception {
        this.mock.perform(delete(PATH + "/remove"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldThrownExceptionWhenSendWrongConfigurationTest() throws Exception {
        orderRequest = new OrderRequest(shippingAddress, null);
        String json = objectMapper.writeValueAsString(orderRequest);

        this.mock.perform(post(PATH + "/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldAddUserOrderTest() throws Exception {
        String json = objectMapper.writeValueAsString(orderRequest);

        this.mock.perform(post(PATH + "/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldAddAdminOrderTest() throws Exception {
        String json = objectMapper.writeValueAsString(orderRequest);

        this.mock.perform(post(PATH + "/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldListAllOrdersTest() throws Exception {
        when(orderService.getAllOrders()).thenReturn(orders);
        String jsonOrders = objectMapper.writeValueAsString(orders);

        this.mock.perform(get(PATH + "/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOrders));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldListUserOrdersTest() throws Exception {
        when(orderService.getUserOrders(username)).thenReturn(orders);
        String jsonOrders = objectMapper.writeValueAsString(orders);

        this.mock.perform(get(PATH + "/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOrders));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldListUserOrdersByAdminTest() throws Exception {
        when(orderService.getUserOrders(username)).thenReturn(orders);
        String jsonOrders = objectMapper.writeValueAsString(orders);

        this.mock.perform(get(PATH + "/list/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOrders));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldForbidListUserOrdersTest() throws Exception {
        this.mock.perform(get(PATH + "/list/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    private Order buildTestOrder() {
        return Order.builder()
                .orderId(id)
                .shippingAddress(shippingAddress)
                .status(status)
                .customer(username)
                .items(items)
                .build();
    }
}
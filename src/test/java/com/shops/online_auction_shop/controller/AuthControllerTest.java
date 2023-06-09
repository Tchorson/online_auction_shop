package com.shops.online_auction_shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shops.online_auction_shop.auth.AuthUser;
import com.shops.online_auction_shop.collection.User;
import com.shops.online_auction_shop.dto.AuthenticationRequest;
import com.shops.online_auction_shop.services.UserService;
import com.shops.online_auction_shop.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest({AuthController.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private String username;
    private String pwd;
    private AuthenticationRequest authenticationRequest;
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
    private AuthUser authUser;
    private User user;
    private String id;
    private String role;
    private String token;
    private final String PATH = "/api/v1/auth";

    @BeforeEach
    void setUp() {
        username = "user";
        pwd = "password";
        authenticationRequest = new AuthenticationRequest(username, pwd);
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, pwd);

        id = "1aeb";
        username = "user";
        pwd = "password";
        role = "ROLE_USER";
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjg2MjYxNjQwLCJpYXQiOjE2ODYyNTgwNDB9.SLObx6iD2sYQVPinMFhYh158kYAng8eaFUMkDDWQ9PY";

        user = new User(id, username, pwd, role);
        authUser = new AuthUser(user);
    }

    @Test
    void shouldAuthUserTest() throws Exception {
        Mockito.when(userService.loadUserByUsername(username)).thenReturn(authUser);
        Mockito.when(jwtUtils.generateToken(authUser)).thenReturn(token);
        String json = objectMapper.writeValueAsString(authenticationRequest);

        this.mock.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is(token)));
    }

    @Test
    void shouldThrownExceptionTest() throws Exception {
        String expectedMessage = "User with such credentials is not found";
        Mockito.when(authenticationManager.authenticate(usernamePasswordAuthenticationToken))
                .thenThrow(new BadCredentialsException(expectedMessage));
        String json = objectMapper.writeValueAsString(authenticationRequest);

        this.mock.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthorized"));
    }
}
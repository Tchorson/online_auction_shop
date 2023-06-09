package com.shops.online_auction_shop.services;

import com.shops.online_auction_shop.auth.AuthUser;
import com.shops.online_auction_shop.collection.User;
import com.shops.online_auction_shop.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private AuthUser authUser;
    private User user;
    private String id;
    private String username;
    private String pwd;
    private String role;


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        id = "1aeb";
        username = "user";
        pwd = "password";
        role = "ROLE_USER";

        user = new User(id, username, pwd, role);
        authUser = new AuthUser(user);
    }


    @Test
    void shouldLoadUserTest() {
        //given
        Mockito.when(userRepository.findUserByUsername(username))
                .thenReturn(user);

        //when
        UserDetails result = userService.loadUserByUsername(username);

        //then
        Assertions.assertEquals(authUser.getUsername(), result.getUsername());
        Assertions.assertEquals(authUser.getPassword(), result.getPassword());
        Assertions.assertEquals(authUser.getAuthorities(), result.getAuthorities());
    }

    @Test
    void shouldThrownUsernameNotFoundExceptionTest() {
        //given
        String expectedMessage = "User with such username is not found";
        Mockito.when(userRepository.findUserByUsername(username))
                .thenThrow(new UsernameNotFoundException(expectedMessage));

        //when
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userRepository.findUserByUsername(username);
        });

        //then
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
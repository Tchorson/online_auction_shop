package com.shops.online_auction_shop.services;

import com.shops.online_auction_shop.auth.AuthUser;
import com.shops.online_auction_shop.collection.User;
import com.shops.online_auction_shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        User user = userRepository.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User with such username is not found");
        }

        return new AuthUser(user);
    }
}

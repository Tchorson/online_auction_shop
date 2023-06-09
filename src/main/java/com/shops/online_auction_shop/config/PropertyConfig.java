package com.shops.online_auction_shop.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource(value={"classpath:jwt.properties"})
public class PropertyConfig {

    @Value( "${secret.key}" )
    private String secret;
}

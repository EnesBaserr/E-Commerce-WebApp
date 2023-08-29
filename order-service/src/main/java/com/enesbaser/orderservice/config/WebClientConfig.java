package com.enesbaser.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //bean created with webClient
    @Bean
    @LoadBalanced//to resolve avaliable client
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}

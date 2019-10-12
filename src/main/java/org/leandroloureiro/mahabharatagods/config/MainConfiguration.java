package org.leandroloureiro.mahabharatagods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class MainConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Executor apiCallExecutor() {
        return Executors.newFixedThreadPool(10, r -> new Thread(r, "API Call Worker Thread"));
    }


}
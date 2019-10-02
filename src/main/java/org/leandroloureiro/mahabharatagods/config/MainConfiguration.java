package org.leandroloureiro.mahabharatagods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MainConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
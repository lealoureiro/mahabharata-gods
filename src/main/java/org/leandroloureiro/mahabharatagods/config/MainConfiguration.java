package org.leandroloureiro.mahabharatagods.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class MainConfiguration {


    @Bean
    public String indianGodsServiceHostname(@Value("${indiangods.service.hostname}") final String indianGodsServiceHostname) {
        return indianGodsServiceHostname;
    }

    @Bean
    public String indianGodServiceHostname(@Value("${indiangod.service.hostname}") final String indianGodServiceHostname) {
        return indianGodServiceHostname;
    }

    @Bean
    public String mahabharataDataSourceHostname(@Value("${indiangod.service.hostname}") final String mahabharataDataSourceHostname) {
        return mahabharataDataSourceHostname;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Executor apiCallExecutor() {

        final var factory = new ThreadFactoryBuilder()
                .setNameFormat("API Call Worker Thread %d")
                .build();

        return Executors.newFixedThreadPool(10, factory);
    }


}
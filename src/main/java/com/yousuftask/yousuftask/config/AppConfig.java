package com.yousuftask.yousuftask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        // The problem: RestTemplate is deprecated in newer Spring versions.
        // Corrected code: Use WebClient instead.
        return new RestTemplate();
    }
}

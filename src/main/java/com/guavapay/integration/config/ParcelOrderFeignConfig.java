package com.guavapay.integration.config;

import com.guavapay.integration.client.ParcelOrderClient;
import com.guavapay.integration.error.ParcelOrderErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {ParcelOrderClient.class})
public class ParcelOrderFeignConfig {

    @Bean
    public ErrorDecoder parcelOrderFeignErrorDecoder() {
        return new ParcelOrderErrorDecoder();
    }
}

package com.binance.dex.service.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;

@SpringBootApplication
public class Application {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public ApplicationConfiguration endpointUri() {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        try {
            applicationConfiguration.setWallet(Wallet.createRandomWallet(BinanceDexEnvironment.TEST_NET));
        } catch (IOException ex) {
            log.error("IOException ex: {}", ex.getMessage(), ex);
        }
        return applicationConfiguration;
    }
}

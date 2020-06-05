package com.joco.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceProviderApplication.class, args);
    }

}

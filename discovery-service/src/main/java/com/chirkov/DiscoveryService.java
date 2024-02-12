package com.chirkov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryService {
    /**
     * A description of the entire Java function.
     *
     * @param  args	description of parameter
     * @return       description of return value
     */
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryService.class, args);
    }
}
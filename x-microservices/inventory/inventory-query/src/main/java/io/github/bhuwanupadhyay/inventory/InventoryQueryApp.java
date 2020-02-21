package io.github.bhuwanupadhyay.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryQueryApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(InventoryQueryApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}

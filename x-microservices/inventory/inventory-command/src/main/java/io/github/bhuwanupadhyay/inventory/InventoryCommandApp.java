package io.github.bhuwanupadhyay.inventory;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryCommandApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(InventoryCommandApp.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }


}

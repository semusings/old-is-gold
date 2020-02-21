package io.github.bhuwanupadhyay.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApp.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

}

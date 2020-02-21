package io.github.bhuwanupadhyay.config;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaServer
public class EurekaApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}

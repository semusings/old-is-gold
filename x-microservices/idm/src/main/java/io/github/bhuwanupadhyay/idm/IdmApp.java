package io.github.bhuwanupadhyay.idm;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IdmApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(IdmApp.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

}

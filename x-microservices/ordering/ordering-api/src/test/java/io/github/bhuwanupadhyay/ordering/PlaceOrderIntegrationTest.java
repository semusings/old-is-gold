package io.github.bhuwanupadhyay.ordering;

import io.github.bhuwanupadhyay.ordering.test.Json;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.bhuwanupadhyay.ordering.test.Scenario.httpScenario;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaceOrderIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void placeOrder() {
        httpScenario(port)
                .placeOrder(
                        Json.create()
                                .setCustomerId("customer-id"))
                .isOk();
    }

}

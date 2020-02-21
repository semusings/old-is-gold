package io.github.bhuwanupadhyay.ordering.command;

import io.github.bhuwanupadhyay.ordering.CommandBus;
import io.github.bhuwanupadhyay.ordering.contract.EndpointResponse;
import io.github.bhuwanupadhyay.ordering.contract.gen.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandEndpointsUnitTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CommandBus bus;

    @InjectMocks
    private CommandEndpoints endpoints;

    @BeforeEach
    void setUp() {
        endpoints = new CommandEndpoints(bus);
    }

    @Test
    void givenOrderRequest_whenPlace_thenShouldPlaced() {
        EndpointResponse<Void> response = endpoints.placeOrder(
                OrderRequest.
                        newBuilder()
                        .setCustomerId("customer-id")
                        .build()
        );
        verify(bus).move(any(OrderPlaceCommand.class));
        assertEquals("PLACED", response.getMessage());
    }
}
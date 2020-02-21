package io.github.bhuwanupadhyay.ordering.query;

import io.github.bhuwanupadhyay.ordering.contract.EndpointResponse;
import io.github.bhuwanupadhyay.ordering.contract.gen.OrderView;
import io.github.bhuwanupadhyay.ordering.query.gen.tables.records.OmsOrderRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static io.github.bhuwanupadhyay.ordering.query.gen.tables.OmsOrder.OMS_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class QueryEndpointsUnitTest {

    private static final int ZERO = 0;
    private QueryEndpoints queryEndpoints;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DSLContext context;

    @BeforeEach
    void setUp() {
        queryEndpoints = new QueryEndpoints(context);
    }

    @Test
    void canReturnNullSafeListOrders() {
        when(context.selectFrom(OMS_ORDER).fetch()).thenReturn(orderRecords());
        EndpointResponse<List<OrderView>> response = queryEndpoints.getOrders();
        assertNotNull(response);
        assertThat(response.getBody()).isNotNull().hasSize(ZERO);
    }

    @Test
    void canReturnAutomaticallyGeneratedAt() {
        when(context.selectFrom(OMS_ORDER).fetch()).thenReturn(orderRecords());
        EndpointResponse<List<OrderView>> response = queryEndpoints.getOrders();
        assertNotNull(response.getGeneratedAt());
    }

    @SuppressWarnings("unchecked")
    private Result<OmsOrderRecord> orderRecords() {
        return mock(Result.class);
    }

}
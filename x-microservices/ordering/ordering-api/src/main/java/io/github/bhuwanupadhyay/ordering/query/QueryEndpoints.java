package io.github.bhuwanupadhyay.ordering.query;


import io.github.bhuwanupadhyay.ordering.contract.EndpointResponse;
import io.github.bhuwanupadhyay.ordering.contract.IEndpoints;
import io.github.bhuwanupadhyay.ordering.contract.IQueryEndpoints;
import io.github.bhuwanupadhyay.ordering.contract.gen.OrderView;
import io.github.bhuwanupadhyay.ordering.query.gen.tables.records.OmsOrderRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.github.bhuwanupadhyay.ordering.query.gen.tables.OmsOrder.OMS_ORDER;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping(IEndpoints.BASE_URI)
public class QueryEndpoints implements IQueryEndpoints {

    private final DSLContext dsl;

    @Override
    @GetMapping
    public EndpointResponse<List<OrderView>> getOrders() {
        Result<OmsOrderRecord> records = dsl.selectFrom(OMS_ORDER).fetch();
        return new EndpointResponse<>(toViews(records));
    }

    private List<OrderView> toViews(Result<OmsOrderRecord> records) {
        return records.stream()
                .map(record -> OrderView.newBuilder()
                        .build()).collect(toList());
    }
}

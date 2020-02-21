package io.github.bhuwanupadhyay.ordering.contract;

import io.github.bhuwanupadhyay.ordering.contract.gen.OrderView;

import java.util.List;

public interface IQueryEndpoints extends IEndpoints {

    EndpointResponse<List<OrderView>> getOrders();

}

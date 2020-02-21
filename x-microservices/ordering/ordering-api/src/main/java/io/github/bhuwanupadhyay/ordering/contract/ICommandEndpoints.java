package io.github.bhuwanupadhyay.ordering.contract;

import io.github.bhuwanupadhyay.ordering.contract.gen.OrderRequest;

public interface ICommandEndpoints {

    EndpointResponse<Void> placeOrder(OrderRequest orderRequest);

}

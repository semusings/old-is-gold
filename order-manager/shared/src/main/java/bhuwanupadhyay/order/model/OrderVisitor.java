package bhuwanupadhyay.order.model;

import bhuwanupadhyay.core.data.Visitor;

public interface OrderVisitor extends Visitor {

    void setOrderId(String orderId);

    void setCustomerId(String customerId);

    void setOrderStatus(String orderStatus);
}

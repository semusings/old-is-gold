package bhuwanupadhyay.order.model;

import bhuwanupadhyay.core.data.Visitable;
import bhuwanupadhyay.core.ddd.AggregateRoot;
import bhuwanupadhyay.core.ddd.DomainEvent;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.*;

import static bhuwanupadhyay.core.railway.message.Message.withError;
import static bhuwanupadhyay.order.model.Order.OrderStatus.*;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends AggregateRoot<String> implements Visitable<OrderVisitor> {
    private String orderId;
    private OrderStatus orderStatus;
    private String customerId;

    public static Result<Order, Message> create(OrderParams params) {
        return Result.with(params, withError("order.parameters.is.required"))
                .ensure(p -> isNotBlank(p.getCustomerId()), withError("customer.id.must.be.not.empty"))
                .map(p -> {
                    Order order = new Order();
                    order.orderId = ofNullable(p.getOrderId()).orElse(randomUUID().toString());
                    order.orderStatus = ofNullable(p.getOrderStatus()).map(OrderStatus::valueOf).orElse(INITIALIZED);
                    order.customerId = p.getCustomerId();
                    return order;
                });
    }

    public void placeOrder() {
        this.orderStatus = PLACED;
        registerEvent(new OrderPlacedEvent(this.orderId));
    }

    public void pay() {
        this.orderStatus = PAID;
        registerEvent(new OrderPaidEvent(this.orderId));
    }

    public void prepareOrder() {
        this.orderStatus = PREPARING;
        registerEvent(new OrderReadyEvent(this.orderId));
    }

    public void shipOrder() {
        this.orderStatus = SHIPPED;
        registerEvent(new OrderDeliveredEvent(this.orderId));
    }

    @Override
    public String getId() {
        return getOrderId();
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.setCustomerId(this.customerId);
        visitor.setOrderId(this.orderId);
        visitor.setOrderStatus(this.orderStatus.name());
    }

    public enum OrderStatus {
        INITIALIZED,
        PLACED,
        PAID,
        PREPARING,
        SHIPPED
    }

    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OrderPlacedEvent extends DomainEvent {
        private String orderId;
    }

    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OrderPaidEvent extends DomainEvent {
        private String orderId;
    }

    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OrderReadyEvent extends DomainEvent {
        private String orderId;
    }

    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OrderDeliveredEvent extends DomainEvent {
        private String orderId;
    }


}

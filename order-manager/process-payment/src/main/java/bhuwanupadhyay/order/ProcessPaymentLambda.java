package bhuwanupadhyay.order;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.order.model.Order;
import bhuwanupadhyay.order.model.Order.OrderPlacedEvent;
import bhuwanupadhyay.order.persistence.OrderRepository;
import com.amazonaws.kinesis.deagg.RecordDeaggregator;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;

import java.util.Optional;

import static bhuwanupadhyay.core.railway.message.Message.withError;
import static bhuwanupadhyay.lambda.api.AwsUtils.publisher;
import static bhuwanupadhyay.lambda.api.AwsUtils.toEventPayload;

public class ProcessPaymentLambda implements RequestHandler<KinesisEvent, Void> {

    private final OrderRepository repository = new OrderRepository();

    @Override
    public Void handleRequest(KinesisEvent kinesisEvent, Context context) {
        RecordDeaggregator.deaggregate(kinesisEvent.getRecords())
                .forEach(record -> toEventPayload(record.getData(), OrderPlacedEvent.class)
                        .map(OrderPlacedEvent::getOrderId)
                        .ifPresent(orderId ->
                                Result.with(order(orderId), withError("Order does not exist with id: " + orderId))
                                        .onSuccess(Order::pay)
                                        .onSuccess(repository::save)
                                        .onSuccess(publisher::publish)
                                        .onFailure(m -> context.getLogger().log(m.getText()))));
        return null;
    }

    private Optional<Order> order(String orderId) {
        return repository.getByOrderId(orderId);
    }

}

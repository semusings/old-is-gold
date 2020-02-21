package bhuwanupadhyay.order;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import bhuwanupadhyay.order.model.Order;
import bhuwanupadhyay.order.persistence.OrderDto;
import bhuwanupadhyay.order.persistence.OrderRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import static bhuwanupadhyay.lambda.api.AwsUtils.*;

public class ReceiveOrderLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final OrderRepository repository = new OrderRepository();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        context.getLogger().log("Receive new order: " + request.getBody());
        OrderDto order = toBody(request.getBody(), OrderDto.class);
        Result<OrderDto, Message> result = Order.create(order)
                .onSuccess(Order::placeOrder)
                .onSuccess(repository::save)
                .onSuccess(publisher::publish)
                .onFailure(m -> context.getLogger().log(m.getText()))
                .map(OrderDto::domainToDto);
        return result.isSuccess() ? ok(result.getValue()) : bad(result.getError().getText());
    }

}

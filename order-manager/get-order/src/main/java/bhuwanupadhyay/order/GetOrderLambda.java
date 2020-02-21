package bhuwanupadhyay.order;

import bhuwanupadhyay.lambda.api.AwsUtils;
import bhuwanupadhyay.order.persistence.OrderDto;
import bhuwanupadhyay.order.persistence.OrderRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class GetOrderLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final OrderRepository repository = new OrderRepository();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        final String orderId = request.getPathParameters().get("orderId");
        return repository.getByOrderId(orderId)
                .map(OrderDto::domainToDto)
                .map(AwsUtils::ok)
                .orElse(AwsUtils.bad("Order does not exist with id:" + orderId));
    }


}

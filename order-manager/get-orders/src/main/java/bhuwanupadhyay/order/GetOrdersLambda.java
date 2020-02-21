package bhuwanupadhyay.order;

import bhuwanupadhyay.lambda.api.AwsUtils;
import bhuwanupadhyay.order.persistence.OrderDto;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.ArrayList;
import java.util.List;

public class GetOrdersLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        List<OrderDto> body = new ArrayList<>();
        OrderDto dto = new OrderDto();
        dto.setOrderId("123123");
        body.add(dto);
        return AwsUtils.ok(new ArrayList<>(body));
    }

}

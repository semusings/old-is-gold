package bhuwanupadhyay.order.persistence;

import bhuwanupadhyay.order.model.Order;
import bhuwanupadhyay.order.model.OrderParams;
import bhuwanupadhyay.order.model.OrderVisitor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.Data;

@SuppressWarnings("WeakerAccess")
@Data
public class OrderDto implements OrderParams, OrderVisitor {
    @DynamoDBHashKey
    private String orderId;
    private String orderStatus;
    private String customerId;

    public static OrderDto domainToDto(Order order) {
        OrderDto dto = new OrderDto();
        order.accept(dto);
        return dto;
    }
}

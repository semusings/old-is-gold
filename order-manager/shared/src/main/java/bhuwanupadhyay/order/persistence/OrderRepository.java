package bhuwanupadhyay.order.persistence;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import bhuwanupadhyay.order.model.Order;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder.standard;
import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameResolver;
import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.builder;

@Slf4j
public class OrderRepository {

    private DynamoDBMapper mapper = new DynamoDBMapper(standard().build(), builder().withTableNameResolver(new OrderTableNameResolver()).build());

    public void save(Order order) {
        mapper.save(OrderDto.domainToDto(order));
    }

    public List<Order> getOrders() {
        return mapper.scan(OrderDto.class, new DynamoDBScanExpression())
                .stream()
                .map(entity -> Order
                        .create(entity)
                        .onFailure(message -> {
                            LOG.warn("Invalid Order: [{}]", message.getText());
                            throw new RuntimeException(message.getText());
                        }).getValue())
                .collect(Collectors.toList());
    }

    public Optional<Order> getByOrderId(String orderId) {
        return Optional.ofNullable(mapper.load(OrderDto.class, orderId))
                .map(entity -> {
                    Result<Order, Message> result = Order.create(entity);
                    return result.isSuccess() ? result.getValue() : null;
                });
    }

    private static class OrderTableNameResolver implements TableNameResolver {

        @Override
        public String getTableName(Class<?> aClass, DynamoDBMapperConfig dynamoDBMapperConfig) {
            return System.getenv("TABLE");
        }
    }
}

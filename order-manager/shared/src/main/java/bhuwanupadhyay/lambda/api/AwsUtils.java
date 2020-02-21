package bhuwanupadhyay.lambda.api;

import bhuwanupadhyay.core.ddd.DomainEvent;
import bhuwanupadhyay.core.ddd.Publisher;
import bhuwanupadhyay.core.ddd.PublisherAdapter;
import bhuwanupadhyay.core.web.Response;
import bhuwanupadhyay.order.persistence.OrderDto;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class AwsUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final Publisher publisher = new PublisherAdapter() {
        @Override
        protected void publish(DomainEvent event) {
            AmazonKinesisClientBuilder
                    .standard().build()
                    .putRecord(
                            new PutRecordRequest()
                                    .withStreamName(System.getenv("ORDER_MANAGER_STREAM_NAME"))
                                    .withPartitionKey(UUID.randomUUID().toString())
                                    .withData(ByteBuffer.wrap(toArray(event))));
        }
    };

    @SneakyThrows
    private static byte[] toArray(DomainEvent event) {
        return MAPPER.writeValueAsBytes(event);
    }

    @SneakyThrows
    public static <T> T toBody(String body, Class<T> valueType) {
        return MAPPER.readValue(body, valueType);
    }

    @SneakyThrows
    public static APIGatewayProxyResponseEvent ok(List<OrderDto> body) {
        return buildOk(Response.ok(body));
    }

    @SneakyThrows
    public static APIGatewayProxyResponseEvent ok(OrderDto body) {
        return buildOk(Response.ok(body));
    }

    @SneakyThrows
    private static APIGatewayProxyResponseEvent buildOk(Object body) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(MAPPER.writeValueAsString(body));
        responseEvent.setStatusCode(HttpStatus.SC_OK);
        return responseEvent;
    }

    @SneakyThrows
    public static APIGatewayProxyResponseEvent bad(String errorMessage) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseEvent.setBody(MAPPER.writeValueAsString(Response.error(errorMessage)));
        return responseEvent;
    }

    public static <T extends DomainEvent> Optional<T> toEventPayload(ByteBuffer array, Class<T> type) {
        try {
            T value = toBody(new String(array.array()), type);
            if (type.getName().equals(value.getEventClassName()))
                return Optional.of(value);
            else
                LOG.warn("Mismatch event type [expecting={}, actual={}]", type.getName(), value.getEventClassName());
        } catch (Exception e) {
            LOG.error("Unable to parse byte buffer [{}]", e);
        }
        return Optional.empty();
    }

    @SneakyThrows
    public static String toString(Object value) {
        return MAPPER.writeValueAsString(value);
    }
}

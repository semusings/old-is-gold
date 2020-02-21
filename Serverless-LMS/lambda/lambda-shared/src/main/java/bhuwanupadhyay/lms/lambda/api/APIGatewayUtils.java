package bhuwanupadhyay.lms.lambda.api;

import bhuwanupadhyay.core.railway.message.Message;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class APIGatewayUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T toBody(String body, Class<T> type) {
        return null;
    }

    @SneakyThrows
    public static APIGatewayProxyResponseEvent bad(Message message) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(MAPPER.writeValueAsString(message.getText()));
        responseEvent.setStatusCode(SC_BAD_REQUEST);
        return responseEvent;
    }

    @SneakyThrows
    public static <T> APIGatewayProxyResponseEvent ok(T body) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(HttpStatus.SC_OK);
        responseEvent.setBody(MAPPER.writeValueAsString(body));
        return responseEvent;
    }

}

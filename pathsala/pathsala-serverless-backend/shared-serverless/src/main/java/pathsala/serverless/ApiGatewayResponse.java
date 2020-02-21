package pathsala.serverless;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ApiGatewayResponse {
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    private static Builder builder() {
        return new Builder();
    }

    public static ApiGatewayResponse bad(String message) {
        return ApiGatewayResponse.builder()
                .setBody(message)
                .setStatusCode(ResponseStatus.BAD_REQUEST.status)
                .setHeaders(headers())
                .build();
    }

    public static <T> ApiGatewayResponse ok(T body) {
        return ApiGatewayResponse.builder()
                .setBody(body)
                .setStatusCode(ResponseStatus.OK.status)
                .setHeaders(headers())
                .build();
    }

    static ApiGatewayResponse serverError(String message) {
        return ApiGatewayResponse.builder()
                .setBody(message)
                .setStatusCode(ResponseStatus.SERVER_ERROR.status)
                .setHeaders(headers())
                .build();
    }

    private static Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "Pathsala");
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        return headers;
    }

    public static <T> ApiGatewayResponse make(Result<Void, Message> result, T body) {
        return result.isFailure() ? ApiGatewayResponse.bad(result.getError().getText()) : ApiGatewayResponse.ok(body);
    }

    public enum ResponseStatus {

        OK(200),
        BAD_REQUEST(400),
        SERVER_ERROR(500);

        private final int status;

        ResponseStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    private static class Builder {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        private int statusCode = ResponseStatus.OK.status;
        private Map<String, String> headers = Collections.emptyMap();
        private Object body;

        Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        Builder setBody(Object body) {
            this.body = body;
            return this;
        }

        ApiGatewayResponse build() {
            String body = null;
            if (this.body != null) {
                try {
                    body = objectMapper.writeValueAsString(this.body);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("failed to serialize object", e);
                }
            }
            return new ApiGatewayResponse(statusCode, body, headers);
        }
    }
}
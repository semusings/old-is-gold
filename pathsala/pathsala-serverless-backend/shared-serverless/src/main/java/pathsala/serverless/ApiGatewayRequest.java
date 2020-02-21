package pathsala.serverless;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.Map;

@Data
public class ApiGatewayRequest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Map<String, String> pathParameters;
    private Map<String, String> stageVariables;
    private Map<String, Object> requestContext;
    private String body;
    private boolean isBase64Encoded;

    public <T> T toBody(Class<T> valueType) {
        try {
            return MAPPER.readValue(body, valueType);
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse body:" + body);
        }
    }
}
package pathsala.serverless.uaa.handler;

import cloud.localstack.LambdaContext;
import org.junit.jupiter.api.Test;
import pathsala.serverless.ApiGatewayRequest;
import pathsala.serverless.ApiGatewayResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pathsala.serverless.ApiGatewayResponse.ResponseStatus.OK;
import static pathsala.serverless.uaa.util.TestUtils.readString;

class AuthenticationHandlerIntegrationTest extends LambdaIntegrationTest {

    @Test
    void can_authenticate_valid_user() {

        AuthenticationHandler handler = new AuthenticationHandler();

        ApiGatewayRequest request = new ApiGatewayRequest();
        request.setBody(readString("login.json"));
        ApiGatewayResponse response = handler.handleRequest(request, new LambdaContext());

        assertEquals(OK.getStatus(), response.getStatusCode());
    }

}
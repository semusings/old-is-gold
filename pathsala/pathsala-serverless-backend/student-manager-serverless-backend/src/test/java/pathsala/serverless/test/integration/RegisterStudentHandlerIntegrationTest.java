package pathsala.serverless.test.integration;

import cloud.localstack.LambdaContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pathsala.serverless.ApiGatewayRequest;
import pathsala.serverless.ApiGatewayResponse;
import pathsala.serverless.student.RegisterStudentHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pathsala.serverless.ApiGatewayResponse.ResponseStatus.OK;
import static pathsala.serverless.utils.Utils.readToString;

@Disabled
class RegisterStudentHandlerIntegrationTest {

    @Test
    void canRegisterStudent() {
        ApiGatewayRequest request = request(readToString("StudentParams.json"));
        ApiGatewayResponse response = new RegisterStudentHandler().handleRequest(request, new LambdaContext());
        assertEquals(OK.getStatus(), response.getStatusCode());
    }

    private ApiGatewayRequest request(String body) {
        ApiGatewayRequest request = new ApiGatewayRequest();
        request.setBody(body);
        return request;
    }

}
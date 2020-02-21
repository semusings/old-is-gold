package pathsala.serverless.uaa.handler;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.apache.http.HttpStatus;
import pathsala.serverless.ApiGatewayResponse;
import pathsala.serverless.HttpEventHandler;
import pathsala.serverless.uaa.payload.SignUpDto;

import java.util.Arrays;
import java.util.List;

import static pathsala.serverless.uaa.handler.Constants.USER_POOL_APP_CLIENT_ID;

public class SignUpHandler extends HttpEventHandler<SignUpDto> {

    @Override
    protected ApiGatewayResponse handle(SignUpDto request, LambdaLogger log) {
        AWSCognitoIdentityProvider identityProvider = AWSCognitoIdentityProviderClientBuilder.standard().build();
        SignUpRequest signUpRequest = new SignUpRequest().
                withClientId(USER_POOL_APP_CLIENT_ID).
                withUsername(request.getUsername()).
                withPassword(request.getPassword());
        signUpRequest.setUserAttributes(attributeDataTypes(request));
        SignUpResult signUpResult = identityProvider.signUp(signUpRequest);
        if (HttpStatus.SC_OK == signUpResult.getSdkHttpMetadata().getHttpStatusCode()) {
            return ApiGatewayResponse.ok(request);
        }
        return ApiGatewayResponse.bad("");
    }

    private List<AttributeType> attributeDataTypes(SignUpDto request) {
        return Arrays.asList(
                attributeType("email", request.getEmail()),
                attributeType("name", request.getName()),
                attributeType("phone_number", request.getPhoneNumber()));
    }

    private AttributeType attributeType(String name, String value) {
        return new AttributeType().withName(name)
                .withValue(value);
    }


}

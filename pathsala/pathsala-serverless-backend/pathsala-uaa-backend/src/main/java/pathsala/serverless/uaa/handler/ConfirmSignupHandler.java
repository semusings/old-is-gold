package pathsala.serverless.uaa.handler;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.apache.http.HttpStatus;
import pathsala.serverless.ApiGatewayResponse;
import pathsala.serverless.HttpEventHandler;
import pathsala.serverless.uaa.payload.ConfirmSignUpDto;

import static pathsala.serverless.uaa.handler.Constants.USER_POOL_APP_CLIENT_ID;

public class ConfirmSignupHandler extends HttpEventHandler<ConfirmSignUpDto> {

    @Override
    protected ApiGatewayResponse handle(ConfirmSignUpDto request, LambdaLogger log) {
        if (HttpStatus.SC_OK == confirmSignUp(request).getSdkHttpMetadata().getHttpStatusCode()) {
            return ApiGatewayResponse.ok("Successfully, confirmed.");
        } else {
            return ApiGatewayResponse.bad("Unsuccessful.");
        }
    }

    private ConfirmSignUpResult confirmSignUp(ConfirmSignUpDto request) {
        AWSCognitoIdentityProvider identityProvider = AWSCognitoIdentityProviderClientBuilder.standard().build();
        return identityProvider.confirmSignUp(
                new ConfirmSignUpRequest().
                        withClientId(USER_POOL_APP_CLIENT_ID).
                        withUsername(request.getUsername()).
                        withConfirmationCode(request.getConfirmationCode()));
    }


}

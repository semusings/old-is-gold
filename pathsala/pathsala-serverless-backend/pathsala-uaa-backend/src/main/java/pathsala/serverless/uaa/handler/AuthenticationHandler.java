package pathsala.serverless.uaa.handler;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.apache.http.HttpStatus;
import pathsala.serverless.ApiGatewayResponse;
import pathsala.serverless.HttpEventHandler;
import pathsala.serverless.uaa.payload.LoginDto;

import java.util.HashMap;

import static pathsala.serverless.uaa.handler.Constants.USER_POOL_APP_CLIENT_ID;

public class AuthenticationHandler extends HttpEventHandler<LoginDto> {

    @Override
    protected ApiGatewayResponse handle(LoginDto request, LambdaLogger log) {
        AWSCognitoIdentityProvider identityProvider = AWSCognitoIdentityProviderClientBuilder.standard().build();
        HashMap<String, String> authParameters = new HashMap<>();
        authParameters.put("USERNAME", request.getUsername());
        authParameters.put("PASSWORD", request.getPassword());
        InitiateAuthResult authResult = identityProvider.initiateAuth(new InitiateAuthRequest()
                .withClientId(USER_POOL_APP_CLIENT_ID)
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withAuthParameters(authParameters));
        if (HttpStatus.SC_OK == authResult.getSdkHttpMetadata().getHttpStatusCode()) {
            return ApiGatewayResponse.ok(authResult.getAuthenticationResult().getAccessToken());
        }
        return ApiGatewayResponse.bad("Unauthorized");
    }

}

package pathsala.serverless.uaa.handler;

import org.junit.jupiter.api.BeforeAll;

import static pathsala.serverless.uaa.handler.Constants.USER_POOL_APP_CLIENT_ID_KEY;
import static pathsala.serverless.uaa.util.TestUtils.setEnv;

abstract class LambdaIntegrationTest {

    @BeforeAll
    static void setUp() {
        setEnv(USER_POOL_APP_CLIENT_ID_KEY, "xxx");
    }
}

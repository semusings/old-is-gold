package bhuwanupadhyay;

import java.util.Optional;

public class TestUtils {

    public static String endpointBaseUri() {
        return Optional.ofNullable(System.getenv("API_ENDPOINT_BASE_URI"))
                .orElse("https://lgm7rkdjm3.execute-api.us-east-1.amazonaws.com/staging");
    }
}
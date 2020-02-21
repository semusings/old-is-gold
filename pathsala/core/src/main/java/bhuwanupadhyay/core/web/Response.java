package bhuwanupadhyay.core.web;

import java.time.LocalDateTime;

public class Response<T> {

    private T body;
    private String errorMessage;
    private LocalDateTime generatedAt;

    private Response(T body, String errorMessage) {
        this.body = body;
        this.errorMessage = errorMessage;
        this.generatedAt = LocalDateTime.now();
    }

    public static <T> Response<T> ok(T result) {
        return new Response<>(result, null);
    }


    public static <T> Response<T> error(String message) {
        return new Response<>(null, message);
    }

    public T getBody() {
        return body;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

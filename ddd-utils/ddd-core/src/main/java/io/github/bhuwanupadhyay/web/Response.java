package io.github.bhuwanupadhyay.web;

import java.util.Date;

public class Response<T> {

    private T body;
    private String errorMessage;
    private Date generatedAt;

    private Response(T body, String errorMessage) {
        this.body = body;
        this.errorMessage = errorMessage;
        this.generatedAt = new Date();
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

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

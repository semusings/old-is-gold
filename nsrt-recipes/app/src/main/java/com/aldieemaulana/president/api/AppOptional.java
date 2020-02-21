package com.aldieemaulana.president.api;

public class AppOptional<T> {

    private final T value;

    private AppOptional(T value) {
        this.value = value;
    }

    static <S> AppOptional<S> empty() {
        return new AppOptional<S>(null);
    }

    static <S> AppOptional<S> of(S value) {
        return new AppOptional<>(value);
    }

    public boolean isSuccessful() {
        return value != null;
    }

    public T body() {
        return value;
    }
}

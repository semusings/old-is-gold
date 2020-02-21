package io.github.bhuwanupadhyay.railway;

import io.github.bhuwanupadhyay.railway.message.Message;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@SuppressWarnings("WeakerAccess")
public abstract class Result<TSuccess, TFailure> {

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> withError(final TFailure error) {
        assertParameterNotNull(error, "ServerError");
        return new Failure<>(error);
    }

    public static <TFailure> Result<Void, TFailure> withoutValue() {
        return new Success<>(null);
    }

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> withValue(final TSuccess value) {
        assertParameterNotNull(value, "Value");
        return new Success<>(value);
    }

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> with(final TSuccess value, final TFailure error) {
        if (value != null) {
            return withValue(value);
        }
        return withError(error);
    }

    public static <TFailure> Result<String, TFailure> withNotEmpty(final String value, final TFailure error) {
        if (isNotBlank(value)) {
            return withValue(value);
        }
        return withError(error);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <TSuccess, TFailure> Result<TSuccess, TFailure> with(
            final Optional<TSuccess> valueOrNothing, final TFailure error) {
        if (valueOrNothing == null || !valueOrNothing.isPresent()) {
            return withError(error);
        }
        return withValue(valueOrNothing.get());
    }

    protected static void assertParameterNotNull(final Object parameter, final String name) {
        if (parameter == null) {
            throw new IllegalArgumentException(String.format("%s may not be null.", name));
        }
    }

    @SafeVarargs
    public static <TSuccess, TFailure> Result<TSuccess, TFailure> combine(
            final Result<TSuccess, TFailure>... results) {
        Result<TSuccess, TFailure> lastResult = null;
        for (Result<TSuccess, TFailure> result : results) {
            lastResult = result;
            if (result.isFailure()) {
                return result;
            }
        }
        return lastResult;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static Result<String, Message> sum(
            final Result<?, Message>... results) {
        for (Result result : results) {
            if (result.isFailure())
                return result;
        }
        return Result.withValue("SUCCEED");
    }

    public abstract boolean isFailure();

    public final boolean isSuccess() {
        return !isFailure();
    }

    public abstract TSuccess getValue();

    public abstract TFailure getError();

    @Override
    public abstract String toString();

    public abstract Result<?, TFailure> combine(final Result<?, TFailure> otherResult);

    public abstract <T> Result<T, TFailure> onSuccess(
            final Supplier<Result<T, TFailure>> function);

    public abstract <T> Result<T, TFailure> onSuccess(
            final Supplier<T> function, final Class<T> clazz);

    public abstract Result<TSuccess, TFailure> onSuccess(final Consumer<TSuccess> function);

    public abstract Result<TSuccess, TFailure> onFailure(final Runnable function);

    public abstract Result<TSuccess, TFailure> onFailureThrow(final Function<TFailure, RuntimeException> function);

    public abstract Result<TSuccess, TFailure> onFailure(final Consumer<TFailure> function);

    public abstract Result<TSuccess, TFailure> onFailure(
            final Predicate<TFailure> predicate, final Consumer<TFailure> function);

    public Result<TSuccess, TFailure> onBoth(
            final Consumer<? super Result<TSuccess, TFailure>> function) {
        function.accept(this);
        return this;
    }

    public abstract Result<TSuccess, TFailure> ensure(
            final Predicate<TSuccess> predicate, final TFailure error);

    public abstract Result<TSuccess, List<TFailure>> ensureAll(final List<Ensure<TSuccess, TFailure>> ensure);

    public abstract <T> Result<T, TFailure> flatMap(
            final Function<TSuccess, Result<T, TFailure>> function);

    public abstract <T> Result<T, TFailure> map(
            final Function<TSuccess, T> function);

    public abstract <T> Result<T, TFailure> ifValueIsPresent(
            final Class<T> innerValue, final TFailure error);
}
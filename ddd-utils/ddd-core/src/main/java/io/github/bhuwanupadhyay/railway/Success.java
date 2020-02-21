package io.github.bhuwanupadhyay.railway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Success<TSuccess, TFailure> extends Result<TSuccess, TFailure> {
    private final Optional<TSuccess> value;

    public Success(final TSuccess value) {
        this.value = Optional.ofNullable(value);
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public TSuccess getValue() {
        if (value.isPresent()) {
            return value.get();
        }
        throw new EmptyResultHasNoValueException();
    }

    @Override
    public TFailure getError() {
        throw new SuccessfulResultHasNoErrorException();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Result (");
        result.append("Success");
        if (value.isPresent()) {
            result.append(" with value <");
            result.append(getValue());
            result.append('>');
        }
        result.append(')');
        return result.toString();
    }

    @Override
    public Result<?, TFailure> combine(
            final Result<?, TFailure> otherResult) {
        if (otherResult.isFailure()) {
            return otherResult;
        }
        return this;
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<Result<T, TFailure>> function) {
        return function.get();
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<T> function, final Class<T> clazz) {
        return onSuccess(() -> new Success<>(function.get()));
    }

    @Override
    public Result<TSuccess, TFailure> onSuccess(final Consumer<TSuccess> function) {
        function.accept(getValue());
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> ensure(
            final Predicate<TSuccess> predicate, final TFailure error) {
        try {
            if (!predicate.test(getValue())) {
                return new Failure<>(error);
            }
        } catch (final EmptyResultHasNoValueException exception) {
            throw exception;
        } catch (final Exception exception) {
            return new Failure<>(error);
        }
        return this;
    }

    @Override
    public Result<TSuccess, List<TFailure>> ensureAll(List<Ensure<TSuccess, TFailure>> ensures) {
        List<TFailure> failures = new ArrayList<>();
        for (Ensure<TSuccess, TFailure> ensure : ensures) {
            Result<TSuccess, TFailure> result = ensure(ensure.getPredicate(), ensure.getFailure());
            if (result.isFailure())
                failures.add(result.getError());
        }
        return failures.isEmpty() ? new Success<>(getValue()) : new Failure<>(failures);
    }

    @Override
    public <T> Result<T, TFailure> flatMap(final Function<TSuccess, Result<T, TFailure>> function) {
        return function.apply(getValue());
    }

    @Override
    public <T> Result<T, TFailure> map(final Function<TSuccess, T> function) {
        return flatMap(function.andThen(Success::new));
    }

    @Override
    public <T> Result<T, TFailure> ifValueIsPresent(
            final Class<T> innerValue, final TFailure error) {
        if (!(getValue() instanceof Optional)) {
            return new Failure<>(error);
        }
        @SuppressWarnings("unchecked") final Optional<T> optional = (Optional<T>) getValue();
        return optional.<Result<T, TFailure>>map(Success::new).orElseGet(() -> new Failure<>(error));
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(final Runnable function) {
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailureThrow(Function<TFailure, RuntimeException> function) {
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(final Consumer<TFailure> function) {
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(
            final Predicate<TFailure> predicate, final Consumer<TFailure> function) {
        return this;
    }
}
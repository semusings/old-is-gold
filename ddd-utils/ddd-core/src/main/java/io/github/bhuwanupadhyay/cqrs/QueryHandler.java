package io.github.bhuwanupadhyay.cqrs;

import io.github.bhuwanupadhyay.railway.Result;
import io.github.bhuwanupadhyay.railway.message.Message;

@FunctionalInterface
public interface QueryHandler<T extends Query, R> {

    Result<R, Message> handle(T query);

}

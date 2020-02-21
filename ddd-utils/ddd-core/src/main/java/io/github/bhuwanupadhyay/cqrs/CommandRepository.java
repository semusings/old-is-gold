package io.github.bhuwanupadhyay.cqrs;

import io.github.bhuwanupadhyay.railway.Result;
import io.github.bhuwanupadhyay.railway.message.Message;

import java.util.List;
import java.util.Optional;

public interface CommandRepository<T, ID> {

    Result<T, Message> save(T entity);

    Optional<T> getOne(ID id);

    List<T> getAll();

}

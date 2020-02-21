package io.github.bhuwanupadhyay.cqrs;

import io.github.bhuwanupadhyay.railway.Result;
import io.github.bhuwanupadhyay.railway.message.Message;

@FunctionalInterface
public interface CommandHandler<T extends Command> {

    Result<Void, Message> handle(T command);

}

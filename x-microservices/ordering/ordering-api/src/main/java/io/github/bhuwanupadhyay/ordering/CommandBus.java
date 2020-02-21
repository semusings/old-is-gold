package io.github.bhuwanupadhyay.ordering;

@FunctionalInterface
public interface CommandBus {

    void move(Object command);

}

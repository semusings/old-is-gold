package io.github.bhuwanupadhyay.ddd;

@FunctionalInterface
public interface Publisher {

    <T> void publish(AggregateRoot<T> aggregateRoot);
}

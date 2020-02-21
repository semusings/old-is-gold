package io.github.bhuwanupadhyay.ddd;

import java.util.List;

public interface EventStore {

    <T> void saveEvents(AggregateRoot<T> root);

    <T> List<DomainEvent> getEvents(AggregateRoot<T> root);
}

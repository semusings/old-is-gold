package io.github.bhuwanupadhyay.ddd;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class PublisherAdapter implements Publisher {

    private final EventStore eventStore;

    @Override
    public <T> void publish(AggregateRoot<T> aggregateRoot) {
        eventStore.saveEvents(aggregateRoot);
        aggregateRoot.getDomainEvents().forEach(this::publish);
        aggregateRoot.clearDomainEvents();
    }

    protected abstract void publish(DomainEvent event);


}

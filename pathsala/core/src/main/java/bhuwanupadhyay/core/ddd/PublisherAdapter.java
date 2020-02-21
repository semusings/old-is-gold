package bhuwanupadhyay.core.ddd;

public abstract class PublisherAdapter implements Publisher {

    @Override
    public <T> void publish(AggregateRoot<T> aggregateRoot) {
        aggregateRoot.
                getDomainEvents().forEach(this::publish);
        aggregateRoot.clearDomainEvents();
    }

    protected abstract void publish(DomainEvent event);


}

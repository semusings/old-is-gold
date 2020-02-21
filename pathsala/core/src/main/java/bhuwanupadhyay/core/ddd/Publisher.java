package bhuwanupadhyay.core.ddd;

@FunctionalInterface
public interface Publisher {

    <T> void publish(AggregateRoot<T> aggregateRoot);
}

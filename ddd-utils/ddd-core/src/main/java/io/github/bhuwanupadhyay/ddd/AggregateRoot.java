package io.github.bhuwanupadhyay.ddd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<ID> extends Entity<ID> {

    private List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Register domain event
     *
     * @param event domain event
     */
    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    /**
     * Clear registered domain events.
     */
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    /**
     * @return list of domain events
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

}

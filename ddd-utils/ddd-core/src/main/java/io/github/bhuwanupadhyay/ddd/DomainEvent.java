package io.github.bhuwanupadhyay.ddd;

import java.util.UUID;

public abstract class DomainEvent {

    private String eventId = UUID.randomUUID().toString();

    private String eventClassName = getClass().getName();

    public String getEventId() {
        return eventId;
    }

    public String getEventClassName() {
        return eventClassName;
    }

}

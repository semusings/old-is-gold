package bhuwanupadhyay.core.ddd;

import java.util.UUID;

public abstract class DomainEvent {

    private String eventId = UUID.randomUUID().toString();

    public String getEventId() {
        return eventId;
    }

}

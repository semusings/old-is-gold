package pathsala.backend;

import bhuwanupadhyay.core.ddd.DomainEvent;
import bhuwanupadhyay.core.ddd.PublisherAdapter;
import org.springframework.stereotype.Component;

@Component
public class MessageQueuePublisher extends PublisherAdapter {

    @Override
    protected void publish(DomainEvent event) {

    }
}

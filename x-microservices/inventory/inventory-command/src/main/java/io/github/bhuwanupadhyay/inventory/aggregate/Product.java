package io.github.bhuwanupadhyay.inventory.aggregate;

import io.github.bhuwanupadhyay.inventory.command.CreateProductCommand;
import io.github.bhuwanupadhyay.inventory.event.ProductCreatedEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.io.Serializable;

@Slf4j
@Getter
@Aggregate
public class Product implements Serializable {

    @AggregateIdentifier
    private String id;
    private String name;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @CommandHandler
    public Product(CreateProductCommand command) {
        LOG.debug("Command: 'CreateProductCommand' received.");

        Assert.hasText(command.getId(), "Missing id");
        Assert.hasText(command.getName(), "Missing product name");

        LOG.debug("Queuing up a new ProductCreatedEvent for product '{}'", command.getId());
        AggregateLifecycle.apply(new ProductCreatedEvent(command.getId(), command.getName()));
    }

    @EventSourcingHandler
    protected void on(ProductCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        LOG.debug("Applied: 'ProductCreatedEvent' [{}] '{}'", event.getId(), event.getName());
    }

}

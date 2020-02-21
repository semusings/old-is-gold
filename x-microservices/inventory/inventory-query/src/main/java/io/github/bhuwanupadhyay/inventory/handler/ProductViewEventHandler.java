package io.github.bhuwanupadhyay.inventory.handler;

import io.github.bhuwanupadhyay.inventory.domain.Product;
import io.github.bhuwanupadhyay.inventory.event.ProductCreatedEvent;
import io.github.bhuwanupadhyay.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductViewEventHandler {

    private final ProductRepository repository;

    @EventHandler
    public void handle(ProductCreatedEvent event) {
        LOG.info("ProductCreatedEvent: [{}] '{}'", event.getId(), event.getName());
        repository.save(new Product(event.getId(), event.getName()));
    }


}

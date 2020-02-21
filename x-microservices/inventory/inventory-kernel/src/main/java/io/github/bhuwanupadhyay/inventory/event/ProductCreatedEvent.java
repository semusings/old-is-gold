package io.github.bhuwanupadhyay.inventory.event;


import lombok.Getter;

@Getter
public class ProductCreatedEvent extends InventoryEvent<String> {

    private final String name;

    public ProductCreatedEvent(String id, String name) {
        super(id);
        this.name = name;
    }
}

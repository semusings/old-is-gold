package io.github.bhuwanupadhyay.inventory.event;

import lombok.Getter;

@SuppressWarnings("WeakerAccess")
@Getter
public abstract class InventoryEvent<ID> {

    public final ID id;

    public InventoryEvent(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }
}
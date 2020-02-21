package io.github.bhuwanupadhyay.inventory.command;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.util.Assert;

@SuppressWarnings("WeakerAccess")
@Getter
public abstract class InventoryCommand<ID> {
    @TargetAggregateIdentifier
    public final ID id;

    public InventoryCommand(ID id) {
        Assert.notNull(id, "Id cannot be null");
        this.id = id;
    }
}
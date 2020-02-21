package k8s.orderg.inventory;

import io.github.bhuwanupadhyay.ddd.data.Visitable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Getter
@AllArgsConstructor
class InventoryProduct implements Visitable<InventoryProductVisitor> {

    private final InvProductRefNo productRefNo;
    private final Integer quantity;

    public boolean isEmpty() {
        return Optional.ofNullable(quantity).filter(q -> q < 1).map(q -> TRUE).orElse(FALSE);
    }

    @Override
    public void accept(InventoryProductVisitor visitor) {

    }

}

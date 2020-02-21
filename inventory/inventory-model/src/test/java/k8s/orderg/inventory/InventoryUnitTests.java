package k8s.orderg.inventory;

import io.github.bhuwanupadhyay.ddd.ddd.DomainEvent;
import org.assertj.core.api.Assertions;
import org.assertj.core.condition.Join;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class InventoryUnitTests {

    private Inventory inventory;
    private InvProductRefNo productRefNo;

    @Before
    public void setUp() {
        inventory = new Inventory();
        productRefNo = new InvProductRefNo(UUID.randomUUID().toString());
    }

    @Test
    public void canNotifyOnProductShortage() {
        inventory.addProduct(new InventoryProduct(productRefNo, 0));
        inventory.notifyOnShortage();
        Assertions.assertThat(inventory.getDomainEvents())
                .isNotEmpty()
                .first()
                .is(new Join<DomainEvent>() {
                    @Override
                    public boolean matches(DomainEvent value) {
                        ProductShortageEvent event = (ProductShortageEvent) value;
                        return event.getProductRefs().contains(productRefNo.getRefNo());
                    }
                });
    }

}
package k8s.orderg.inventory;

import io.github.bhuwanupadhyay.ddd.ddd.DomainEvent;
import io.github.bhuwanupadhyay.ddd.ddd.RefNo;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class ProductShortageEvent extends DomainEvent {

    private List<String> productRefs;

    public ProductShortageEvent(List<String> productRefs) {
        this.productRefs = productRefs;
    }

    public static ProductShortageEvent create(List<InventoryProduct> products) {
        return new ProductShortageEvent(products.stream()
                .map(InventoryProduct::getProductRefNo)
                .map(RefNo::getRefNo)
                .collect(toList()));
    }
}

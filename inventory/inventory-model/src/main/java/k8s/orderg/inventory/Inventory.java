package k8s.orderg.inventory;

import io.github.bhuwanupadhyay.ddd.data.Visitable;
import io.github.bhuwanupadhyay.ddd.ddd.AggregateRoot;
import io.github.bhuwanupadhyay.ddd.railway.Result;
import io.github.bhuwanupadhyay.ddd.railway.message.Message;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Inventory extends AggregateRoot<InvRefNo> implements Visitable<InventoryVisitor> {

    private InvRefNo refNo;
    @Getter
    private List<InventoryProduct> products = new ArrayList<>();

    public static Result<Inventory, Message> create(String jsonBody) {
        return Result.with(null, null);
    }

    @Override
    public InvRefNo getId() {
        return refNo;
    }

    public void addProduct(InventoryProduct product) {
        this.products.add(product);
    }

    public List<InventoryProduct> shortageProducts() {
        return this.products.stream().filter(InventoryProduct::isEmpty).collect(toList());
    }

    public void notifyOnShortage() {
        List<InventoryProduct> products = this.shortageProducts();
        if (!products.isEmpty())
            registerEvent(ProductShortageEvent.create(products));
    }

    @Override
    public void accept(InventoryVisitor visitor) {
        visitor.setInvRefNo(refNo.getRefNo());
    }

}

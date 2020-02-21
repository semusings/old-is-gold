package k8s.orderg.inventory;

import io.github.bhuwanupadhyay.ddd.data.Visitor;

import java.util.List;

public interface InventoryVisitor extends Visitor {

    void setInvRefNo(String refNo);

    void setProducts(List<InventoryProductParams> products);

}

package k8s.orderg.inventory;

import java.util.List;

public interface InventoryParams {

    String getInvRefNo();

    List<InventoryProductParams> getProducts();

}

package k8s.orderg.inventory;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private List<InventoryItem> items = new ArrayList<>();

    public InventoryService() {
        this.items.add(new InventoryItem());
    }

    public Flux<InventoryItem> list() {
        return Flux.fromIterable(this.items);
    }

}

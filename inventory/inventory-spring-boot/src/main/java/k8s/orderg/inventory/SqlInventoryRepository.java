package k8s.orderg.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

interface SqlInventoryRepository extends JpaRepository<InventoryItem, String> {
}

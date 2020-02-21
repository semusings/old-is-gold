package io.github.bhuwanupadhyay.inventory.repository;

import io.github.bhuwanupadhyay.inventory.domain.Product;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends ReadOnlyPagingAndSortingRepository<Product, String> {

}

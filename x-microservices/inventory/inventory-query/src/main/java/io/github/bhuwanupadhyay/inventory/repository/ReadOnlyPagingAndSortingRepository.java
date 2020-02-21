package io.github.bhuwanupadhyay.inventory.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@SuppressWarnings("NullableProblems")
@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository<T, ID> extends PagingAndSortingRepository<T, ID> {

    @Override
    @RestResource(exported = false)
    <S extends T> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(ID id);

    @Override
    @RestResource(exported = false)
    void delete(T entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends T> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

}

package io.github.bhuwanupadhyay.ddd;

import java.util.Objects;

/**
 * @param <ID> identity
 */
public abstract class Entity<ID> {

    public abstract ID getId();

    @Override
    public String toString() {
        return this.getClass().getName() + "{" + "id=" + getId() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

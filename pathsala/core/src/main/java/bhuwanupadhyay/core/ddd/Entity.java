package bhuwanupadhyay.core.ddd;

import java.util.Objects;

/**
 * @param <ID> identity
 */
public abstract class Entity<ID> {

    private final ID id;

    protected Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

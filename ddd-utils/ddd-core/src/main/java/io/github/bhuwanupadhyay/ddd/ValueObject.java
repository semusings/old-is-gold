package io.github.bhuwanupadhyay.ddd;

import java.util.Objects;

public abstract class ValueObject {

    @Override
    public int hashCode() {
        return this.hash();
    }

    protected abstract int hash();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject valueObject = (ValueObject) o;
        return Objects.equals(this, valueObject);
    }

}

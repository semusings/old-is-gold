package io.github.bhuwanupadhyay.data;

public interface Visitable<T extends Visitor> {

    void accept(T visitor);
}

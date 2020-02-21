package io.github.bhuwanupadhyay.data;

public interface Assembler<T, A> {

    T writeDto(A root);

    A write(T dto);

}
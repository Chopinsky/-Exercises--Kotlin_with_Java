package com.company;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}

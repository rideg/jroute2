package org.boroka.service.callback;


@FunctionalInterface
public interface FourParamsMethod<T1, T2, T3, T4> {

    void call(T1 p1, T2 p2, T3 p3, T4 p4);

}

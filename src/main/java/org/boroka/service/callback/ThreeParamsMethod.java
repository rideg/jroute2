package org.boroka.service.callback;


@FunctionalInterface
public interface ThreeParamsMethod<T1, T2, T3> {

    void call(T1 p1, T2 p2, T3 p3);

}

package org.boroka.service.callback;


@FunctionalInterface
public interface TwoParamsMethod<T1, T2> {

    void call(T1 p1, T2 p2);

}

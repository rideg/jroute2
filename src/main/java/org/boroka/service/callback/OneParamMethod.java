package org.boroka.service.callback;


@FunctionalInterface
public interface OneParamMethod<T1> {

    void call(T1 p1);

}

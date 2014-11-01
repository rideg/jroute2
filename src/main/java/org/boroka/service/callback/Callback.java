package org.boroka.service.callback;

public final class Callback<Method> {

    public static Callback<NoParamMethod> callback(NoParamMethod m) {
        return new Callback<>(m);
    }

    public static <T1> Callback<OneParamMethod<T1>> callback(OneParamMethod<T1> m) {
        return new Callback<>(m);
    }

    public static <T1, T2>
    Callback<TwoParamsMethod<T1, T2>> callback(TwoParamsMethod<T1, T2> m) {
        return new Callback<>(m);
    }

    public static <T1, T2, T3>
    Callback<ThreeParamsMethod<T1, T2, T3>> callback(ThreeParamsMethod<T1, T2, T3> m) {
        return new Callback<>(m);
    }

    public static <T1, T2, T3, T4>
    Callback<FourParamsMethod<T1, T2, T3, T4>> callback(FourParamsMethod<T1, T2, T3, T4> m) {
        return new Callback<>(m);
    }

    public static <T1, T2, T3, T4, T5>
    Callback<FiveParamsMethod<T1, T2, T3, T4, T5>> callback(FiveParamsMethod<T1, T2, T3, T4, T5> m) {
        return new Callback<>(m);
    }

    private Method method;

    Callback(Method method) {
        this.method = method;
    }

    public Method get() {
        return method;
    }


}

package org.boroka.router;

import org.boroka.ioc.Context;

public class Routes {

    public Routes(Context context) {
    }

    public Routes() {
    }

    public static <T> T calls(Class<T> inClass) {
        return null;
    }

    public static <T> T use(Class<T> inClass) {
        return null;
    }

    public void get(String pattern, Runnable method) {
    }

    public void delete(String pattern, Runnable method) {
    }

    public void post(String pattern, Runnable method) {
    }

    public void put(String pattern, Runnable method) {
    }

    public void wrapper(Runnable wrapper) {
    }
}

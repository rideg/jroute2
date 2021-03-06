package org.jroute.ioc;

import java.util.concurrent.Callable;

public interface ExecutionStrategy<T> {

    Callable<T> task(Class<?> subject);

}

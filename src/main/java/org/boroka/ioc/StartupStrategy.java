package org.boroka.ioc;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

public class StartupStrategy implements ExecutionStrategy<Object> {

    private final Instantiator instantiator;

    public StartupStrategy(Instantiator instantiator) {
        this.instantiator = instantiator;
    }

    @Override
    public Callable<Object> task(Class<?> subject) {
        return () -> {
            Object o = instantiator.newInstance(subject);
            Optional<Method> startup = findStartup(subject);
            if(startup.isPresent()) {
                startup.get().invoke(o);
            }
            return o;
        };
    }

    private Optional<Method> findStartup(Class<?> subject) {
        return asList(subject.getMethods()).stream()
                .filter(m -> "startup".equals(m.getName()))
                .findFirst();
    }

}

package org.jroute.ioc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

public class ShutdownStrategy implements ExecutionStrategy<Void> {

    private final Map<Class<?>, ?> instances;

    public ShutdownStrategy(Map<Class<?>, ?> instances) {
        this.instances = instances;
    }

    @Override
    public Callable<Void> task(Class<?> subject) {
        return () -> {
            Optional<Method> shutdown =
                    asList(subject.getDeclaredMethods())
                            .stream()
                            .filter(m -> "shutdown".equals(m.getName()))
                            .findFirst();

            if (shutdown.isPresent()) {
                shutdown.get().invoke(instances.get(subject));
            }
            return null;
        };
    }
}

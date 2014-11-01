package org.boroka.ioc;

import java.lang.reflect.Constructor;
import java.util.Map;

import static java.util.Arrays.asList;

class DefaultInstantiator implements Instantiator {

    private final Map<Class<?>, Object> instances;

    DefaultInstantiator(Map<Class<?>, Object> instances) {
        this.instances = instances;
    }

    @Override
    public <T> T newInstance(Class<T> inClass) throws ReflectiveOperationException {
        return newInstance(getConstructor(inClass));
    }


    private Constructor<?> getConstructor(Class<?> subject) {
        return subject.getConstructors()[0];
    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(Constructor<?> constructor) throws ReflectiveOperationException {
        return (T) constructor.newInstance(
                asList(constructor.getParameterTypes())
                        .stream()
                        .map(instances::get)
                        .toArray());
    }
}

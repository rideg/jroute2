package org.boroka.ioc;

public interface Instantiator {

    <T> T newInstance(Class<T> inClass) throws ReflectiveOperationException;

}

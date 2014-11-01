package org.boroka.service;

import org.boroka.service.callback.Callback;
import org.boroka.service.queue.Buffer;

import java.lang.reflect.Field;

import static java.lang.Thread.currentThread;
import static java.lang.reflect.Proxy.newProxyInstance;

public class ServiceProxyCreator {


    private final BufferAddressBook book;

    public ServiceProxyCreator(BufferAddressBook book) {
        this.book = book;
    }

    @SuppressWarnings("unchecked")
    public <T> T proxy(T instance, Buffer buffer) {
        Class<?> aClass = instance.getClass();
        return (T) newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), (proxy, method, args) -> {
            prepareCallbacks(args);
            buffer.push(new Task(instance, method, args));
            return null;
        });
    }

    private void prepareCallbacks(Object[] args) {
        for(Object o : args) {
            if(o.getClass() == Callback.class) {
                wrapCallback(o);
            }
        }
    }

    private void wrapCallback(Object o) {
        try {
            Field method = o.getClass().getDeclaredField("method");
            method.setAccessible(true);
            method.set(o, createProxy(method.get(o), book.getQueue(currentThread().getId())));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createProxy(T subject, Buffer buffer) {
        Class<?> s = subject.getClass();
        return (T) newProxyInstance(s.getClassLoader(), s.getInterfaces(),
                (proxy, method, args) -> {
                    buffer.push(new Task(subject, method, args));
                    return null;
                });
    }

}

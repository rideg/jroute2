package org.jroute.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Task {

    private final Object subject;
    private final Method method;
    private final Object[] args;

    public Task(Object subject, Method method, Object[] args) {
        this.subject = subject;
        this.method = method;
        this.args = args;
    }

    public void execute() throws InvocationTargetException, IllegalAccessException {
        method.invoke(subject, args);
    }
}

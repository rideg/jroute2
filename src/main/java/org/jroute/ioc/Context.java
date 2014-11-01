package org.jroute.ioc;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Arrays.asList;

public class Context {

    private final Set<Class<?>> classes = new HashSet<>();
    private Map<Class<?>, Object> instances;
    private final GroupExecutor executor;
    private HashMap<Class<?>, Object> unManaged;

    public Context() {
        this(Executors.newSingleThreadExecutor());
    }

    public Context(ExecutorService service) {
        this.executor = new GroupExecutor(service);
        unManaged = new HashMap<>();
        addUnManaged(this);
    }

    public void addUnManaged(Object service) {
        unManaged.put(service.getClass(), service);
    }

    public void add(Class<?> object) {
        classes.add(object);
    }

    public void startup() throws Exception {
        instances = new HashMap<>(unManaged);
        dependencies().stream().forEach(g -> createInstances(g).stream().forEach(i -> instances.put(i.getClass(), i)));
    }

    private List<Object> createInstances(Set<Class<?>> g) {
        return executor.<Object>execute(g,
                new StartupStrategy(new DefaultInstantiator(instances)));
    }

    public void shutdown() throws Exception {
        List<Set<Class<?>>> dependencies = dependencies();
        Collections.reverse(dependencies);
        dependencies.stream().forEach(g -> executor.<Void>execute(g, new ShutdownStrategy(instances)));
    }

    private List<Set<Class<?>>> dependencies() {
        return new DependencyStructure(classes).create();
    }

    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> inClass) throws ReflectiveOperationException {
        Constructor<T> constructor = (Constructor<T>) inClass.getConstructors()[0];
        return constructor.newInstance(asList(constructor.getParameterTypes()).stream().map(instances::get).toArray());
    }

}

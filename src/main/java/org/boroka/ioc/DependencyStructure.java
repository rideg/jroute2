package org.boroka.ioc;

import java.lang.reflect.Constructor;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class DependencyStructure {

    private final Set<Class<?>> classes;
    private final Set<Class<?>> unManaged;
    private final List<Set<Class<?>>> groups;
    private final Stack<Class<?>> stack;

    public DependencyStructure(Set<Class<?>> classes) {
        this(classes, Collections.emptySet());
    }

    public DependencyStructure(Set<Class<?>> classes, Set<Class<?>> unManaged) {
        this.classes = classes;
        this.unManaged = unManaged;
        groups = new LinkedList<>();
        stack = new Stack<>();

    }

    public List<Set<Class<?>>> create() {
        classes.stream().forEach(this::safeProcess);
        return groups;
    }

    private void safeProcess(Class<?> service) {
        stack.push(service);
        process(service);
        stack.pop();
    }


    private void process(Class<?> service) {
        List<Class<?>> dependencies = getDependencies(service);

        dependencies.stream()
                .filter(d -> !unManaged.contains(d))
                .forEach(d -> {
                    check(d);
                    safeProcess(d);
                });

        for(Set<Class<?>> group : groups) {
            if(dependencies.isEmpty()) {
                group.add(service);
                return;
            }
            dependencies.removeAll(dependencies.stream().filter(group::contains).collect(toList()));
        }
        newGroup().add(service);
    }

    private Set<Class<?>> newGroup() {
        HashSet<Class<?>> group = new HashSet<>();
        groups.add(group);
        return group;
    }

    private void check(Class<?> dependency) {
        checkExistence(dependency);
        checkCircularity(dependency);
    }

    private void checkExistence(Class<?> dependency) {
        if(!classes.contains(dependency)) {
            throw new IllegalStateException("Cannot find dependency " + dependency);
        }
    }

    private List<Class<?>> getDependencies(Class<?> service) {
        List<Class<?>> dependencies = constructorDependencies(service);
        dependencies.addAll(annotationDependencies(service));
        return dependencies;
    }

    private Collection<Class<?>> annotationDependencies(Class<?> service) {
        DependsOn annotation = service.getAnnotation(DependsOn.class);
        return annotation == null ? Collections.emptyList() : asList(annotation.value());
    }

    private List<Class<?>> constructorDependencies(Class<?> service) {
        Constructor<?>[] constructors = service.getConstructors();
        check(service, constructors);
        return new ArrayList<>(asList(constructors[0].getParameterTypes()));
    }

    private void check(Class<?> service, Constructor<?>[] constructors) {
        if(constructors.length == 0) {
            throw new IllegalStateException("Cannot find public constructor for " + service);
        }
        if(constructors.length > 1) {
            throw new IllegalStateException(
                    service + " has more than one public constructor! (Which one should be used?)");
        }
    }

    private void checkCircularity(Class<?> dependency) {
        if(stack.contains(dependency)) {
            throw new IllegalStateException("Found circular dependency!");
        }
    }

}

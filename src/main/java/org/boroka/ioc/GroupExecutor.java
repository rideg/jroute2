package org.boroka.ioc;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

public class GroupExecutor {

    private final ExecutorService executor;

    public GroupExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public <T> List<T> execute(Set<Class<?>> group, ExecutionStrategy<T> strategy) {
        return group.stream().map(s -> executor.submit(strategy.task(s))).map(this::get).collect(toList());
    }

    private <T> T get(Future<T> f) {
        try {
            return f.get();
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }

}

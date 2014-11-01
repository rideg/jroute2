package org.jroute.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PooledExecutor {

    private static final long DEFAULT_TIMEOUT = 10;
    private final List<Thread> threads;
    private final int parallelism;
    private final ThreadFactory factory;

    public PooledExecutor(int parallelism, ThreadFactory factory) {
        this.parallelism = parallelism;
        this.factory = factory;
        threads = new ArrayList<>(this.parallelism);
    }

    public void submit(Runnable run) {
        for(int i = 0; i < parallelism; i++) {
            Thread t = factory.newThread(run);
            threads.add(t);
            t.start();
        }
    }

    public void shutdown() {
        for(Thread t : threads) {
            try {
                t.interrupt();
                t.join(SECONDS.toMillis(DEFAULT_TIMEOUT));
            } catch(InterruptedException ignored) {
            }
        }
    }
}

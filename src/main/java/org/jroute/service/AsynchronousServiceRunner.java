package org.jroute.service;

import org.jroute.service.queue.Buffer;

public class AsynchronousServiceRunner {

    private final PooledExecutor executor;
    private final Buffer taskBuffer;

    public AsynchronousServiceRunner(PooledExecutor executor, Buffer taskBuffer) {
        this.executor = executor;
        this.taskBuffer = taskBuffer;
    }

    public void startup() {
        executor.submit(this::run);
    }

    private void run() {
        try {
            Task[] tasks = new Task[taskBuffer.size() >> 1 + 1];
            while(true) {
                int amount = taskBuffer.read(tasks);
                for(int i = 0; i < amount; i++) {
                    try {
                        tasks[i].execute();
                    } catch(Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch(InterruptedException e) {
            // shutdown
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}

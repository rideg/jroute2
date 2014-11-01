package org.boroka.service.queue;

import org.boroka.service.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Buffer {

    private final BlockingQueue<Task> queue;

    public Buffer(int size) {
        queue = new ArrayBlockingQueue<Task>(size);
    }

    public int size() {
        return queue.size();
    }

    public int read(Task[] tasks) throws InterruptedException {
        tasks[0] = queue.take();
        return 1;
    }

    public void push(Task task) throws InterruptedException {
        queue.put(task);
    }
}

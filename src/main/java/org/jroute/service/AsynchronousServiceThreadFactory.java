package org.jroute.service;

import org.jroute.service.queue.Buffer;

import java.util.concurrent.ThreadFactory;

public class AsynchronousServiceThreadFactory implements ThreadFactory {

    private final Buffer buffer;
    private final String name;
    private long id = 0;
    private BufferAddressBook book;

    public AsynchronousServiceThreadFactory(Buffer buffer, String name, BufferAddressBook book) {
        this.buffer = buffer;
        this.name = name;
        this.book = book;
    }

    @Override
    public Thread newThread(Runnable run) {
        Thread t = new Thread(run, "[" + name + "] executor - " + getId());
        book.addQueue(buffer, t.getId());
        return t;
    }

    private long getId() {
        return id++;
    }
}

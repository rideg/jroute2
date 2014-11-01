package org.jroute.service;

import org.jroute.service.queue.Buffer;

import static java.util.Arrays.copyOf;

public class BufferAddressBook {

    public static final int DEFAULT_SIZE = 100;

    private Buffer[] buffers;

    public BufferAddressBook() {
        this.buffers = new Buffer[DEFAULT_SIZE];
    }

    public synchronized void addQueue(Buffer buffer, long threadId) {
        extendIfNeeded(threadId);
        this.buffers[((int) threadId)] = buffer;
    }

    private void extendIfNeeded(long threadId) {
        if(threadId >= buffers.length) {
            this.buffers = copyOf(this.buffers, (int) threadId * 2);
        }
    }

    public Buffer getQueue(long threadId) {
        return this.buffers[(int) threadId];
    }
}

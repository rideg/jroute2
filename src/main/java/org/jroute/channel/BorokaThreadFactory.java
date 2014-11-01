package org.jroute.channel;

import java.util.concurrent.ThreadFactory;

public class BorokaThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "Boroka Server Thread");
    }
}

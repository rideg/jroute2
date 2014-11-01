package org.jroute.channel;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadFactory;

public class BorokaThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, "Boroka Server Thread");
    }
}

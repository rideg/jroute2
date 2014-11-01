package org.boroka.channel;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, ExecutionChain> {

    @Override
    public void completed(AsynchronousSocketChannel result, ExecutionChain attachment) {
    }

    @Override
    public void failed(Throwable exc, ExecutionChain attachment) {
    }
}

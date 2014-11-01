package org.boroka;

import org.boroka.channel.BorokaThreadFactory;
import org.boroka.channel.ConnectionHandler;
import org.boroka.channel.ExecutionChain;
import org.boroka.ioc.Context;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

import static java.net.StandardSocketOptions.*;
import static java.nio.channels.AsynchronousChannelGroup.withFixedThreadPool;
import static java.nio.channels.AsynchronousServerSocketChannel.open;

public class Server {

    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_BUFFER_SIZE = 4096;

    private final Context context;

    public Server() {
        context = new Context();
    }

    public void start() throws Exception {
        AsynchronousServerSocketChannel channel = open(withFixedThreadPool(1, new BorokaThreadFactory()));
        if(channel.isOpen()) {
            channel.setOption(SO_KEEPALIVE, true);
            channel.setOption(SO_RCVBUF, DEFAULT_BUFFER_SIZE);
            channel.setOption(SO_SNDBUF, DEFAULT_BUFFER_SIZE);
            channel.bind(new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT));
            channel.accept(createChain(), new ConnectionHandler());
        }
    }

    private ExecutionChain createChain() throws Exception {
        context.startup();
        return new ExecutionChain();
    }


}

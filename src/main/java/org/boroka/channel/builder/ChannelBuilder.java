package org.boroka.channel.builder;

import org.boroka.channel.InBoundNode;
import org.boroka.channel.InOutNode;
import org.boroka.channel.OutBoundNode;

public class ChannelBuilder<ForwardIn, BackwardOut> {


    public <ForwardOut> ChannelBuilder<ForwardOut, BackwardOut> use(InBoundNode<ForwardIn, ForwardOut> node) {
        return null;
    }

    public <BackwardIn> ChannelBuilder<ForwardIn, BackwardIn> use(OutBoundNode<BackwardIn, BackwardOut> node) {
        return null;
    }

    public <ForwardOut, BackwardIn> ChannelBuilder<ForwardOut, BackwardIn>
    use(InOutNode<ForwardIn, ForwardOut, BackwardIn, BackwardOut> node) {
        return null;
    }

}

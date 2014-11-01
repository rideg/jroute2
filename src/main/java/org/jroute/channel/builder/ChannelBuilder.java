package org.jroute.channel.builder;

import org.jroute.channel.InBoundNode;
import org.jroute.channel.InOutNode;
import org.jroute.channel.OutBoundNode;

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

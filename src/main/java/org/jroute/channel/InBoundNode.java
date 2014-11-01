package org.jroute.channel;

import java.util.function.Consumer;

public interface InBoundNode<In, Out> {

    void inEvent(In data, Consumer<Out> next);

}

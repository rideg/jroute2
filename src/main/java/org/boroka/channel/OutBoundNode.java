package org.boroka.channel;

import java.util.function.Consumer;

public interface OutBoundNode<In, Out> {

    void outEvent(In data, Consumer<Out> next);

}

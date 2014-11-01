package org.boroka.channel;

import java.util.function.Consumer;

public interface InBoundNode<In, Out> {

    void inEvent(In data, Consumer<Out> next);

}

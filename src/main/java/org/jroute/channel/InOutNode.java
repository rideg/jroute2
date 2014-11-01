package org.jroute.channel;

import java.util.function.Consumer;

public interface InOutNode<In, Out, BackIn, BackOut> extends OutBoundNode<BackIn, BackOut> {

    void inEvent(In data, Consumer<Out> next, Consumer<BackOut> prev);
}

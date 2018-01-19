package io.xylitol.util.concurrent;

import java.util.Arrays;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class DefaultXFutureListeners {

    private GenericXFutureListener[] listeners;
    private int size;

    DefaultXFutureListeners(
            GenericXFutureListener<? extends XFuture<?>> first, GenericXFutureListener<? extends XFuture<?>> second) {
        listeners = new GenericXFutureListener[2];
        listeners[0] = first;
        listeners[1] = second;
        size = 2;

    }

    public void add(GenericXFutureListener<? extends XFuture<?>> l) {
        GenericXFutureListener[] listeners = this.listeners;
        final int size = this.size;
        if (size == listeners.length) {
            this.listeners = listeners = Arrays.copyOf(listeners, size << 1);
        }
        listeners[size] = l;
        this.size = size + 1;


    }

    public void remove(GenericXFutureListener<? extends XFuture<?>> l) {
        final GenericXFutureListener[] listeners = this.listeners;
        int size = this.size;
        for (int i = 0; i < size; i++) {
            if (listeners[i] == l) {
                int listenersToMove = size - i - 1;
                if (listenersToMove > 0) {
                    System.arraycopy(listeners, i + 1, listeners, i, listenersToMove);
                }
                listeners[--size] = null;
                this.size = size;


                return;
            }
        }
    }

    public GenericXFutureListener[] listeners() {
        return listeners;
    }

    public int size() {
        return size;
    }


}

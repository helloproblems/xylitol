package io.xylitol.util.concurrent;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public interface Valid<V> {
    XFuture<V> setSuccess(V result);


    boolean trySuccess(V result);


    XFuture<V> setFailure(Throwable cause);


    boolean tryFailure(Throwable cause);
}

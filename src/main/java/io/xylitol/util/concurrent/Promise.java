package io.xylitol.util.concurrent;

/**
 * Created on 2018/1/19.
 * <p>
 * 有返回的XFuture
 *
 * @author xuyandong
 */
public interface Promise<V> extends XFuture<V> {

    Promise<V> setSuccess(V result);


    boolean trySuccess(V result);


    Promise<V> setFailure(Throwable cause);


    boolean tryFailure(Throwable cause);
}

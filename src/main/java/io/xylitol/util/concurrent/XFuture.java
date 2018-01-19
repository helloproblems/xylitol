package io.xylitol.util.concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2017/12/29.
 *
 * @author xuyandong
 */
public interface XFuture<V> extends Future<V> {
    /**
     * Returns {@code true} 如果运行成功
     */
    boolean isSuccess();

    /**
     * 是否可以取消
     */
    boolean isCancellable();

    /**
     * the cause of the failure.
     * {@code null} if succeeded or this future is not
     * completed yet.
     */
    Throwable cause();

    /**
     * 添加单个XFutureListener
     */
    XFuture<V> addListener(GenericXFutureListener<? extends XFuture<? super V>> listener);

    /**
     * 添加多个XFutureListener
     */
    XFuture<V> addListeners(GenericXFutureListener<? extends XFuture<? super V>>... listeners);

    /**
     * 删除单个当前的Future的Listener
     */
    XFuture<V> removeListener(GenericXFutureListener<? extends XFuture<? super V>> listener);

    /**
     * 删除多个当前的Future的Listener
     */
    XFuture<V> removeListeners(GenericXFutureListener<? extends XFuture<? super V>>... listeners);


    /**
     * 立即返回结果 值可能为{@code null}
     * <p>
     * future执行成功的时候也可能返回 {@code null} 也需要检查
     * future 成功时{@link #isDone()} 不会返回 {@code null} .
     */
    V getNow();


    /**
     * 等待当前的future完成
     */
    XFuture<V> await() throws InterruptedException;


    /**
     * 等待当前future在规定时间内完成
     *
     * @return {@code true} 当且仅当future在规定时间内完成
     * @throws InterruptedException 如果当前线程被中断
     */
    boolean await(long timeout, TimeUnit unit) throws InterruptedException;
}

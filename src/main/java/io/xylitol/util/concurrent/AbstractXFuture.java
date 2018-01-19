package io.xylitol.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2018/1/18.
 * <p>
 * XFuture get的基础实现
 *
 * @author xuyandong
 */
public abstract class AbstractXFuture<V> implements XFuture<V> {

    /**
     * 立即获取结果
     *
     * @return 返回结果V
     * @throws InterruptedException 线程被中断
     * @throws ExecutionException   异常
     */
    @Override
    public V get() throws InterruptedException, ExecutionException {
        await();

        Throwable cause = cause();
        if (cause == null) {
            return getNow();
        }
        if (cause instanceof CancellationException) {
            throw (CancellationException) cause;
        }
        throw new ExecutionException(cause);
    }

    /**
     * 在规定时间内获取结果
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 返回结果V
     * @throws InterruptedException 线程被中断时
     * @throws ExecutionException   异常
     * @throws TimeoutException     未在规定时间返回结果
     */
    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (await(timeout, unit)) {
            Throwable cause = cause();
            if (cause == null) {
                return getNow();
            }
            if (cause instanceof CancellationException) {
                throw (CancellationException) cause;
            }
            throw new ExecutionException(cause);
        }
        throw new TimeoutException();
    }
}

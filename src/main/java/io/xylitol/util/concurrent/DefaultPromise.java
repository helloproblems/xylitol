package io.xylitol.util.concurrent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static io.xylitol.util.internal.ObjectUtil.checkNotNull;

/**
 * Created on 2018/1/19.
 * <p>
 * 默认实现 Promise
 *
 * @author xuyandong
 */
public abstract class DefaultPromise<V> extends AbstractXFuture<V> implements Promise<V> {

    /**
     * 对result 的原子更新操作
     */
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");

    private volatile Object result; // 保存结果或者异常

    private Object listeners;

    private volatile short waiters;//线程等待数量

    private final static short WAITERS_MAX = Short.MAX_VALUE;

    /**
     * 是否正在同步的状态
     */
    private boolean notifyingListeners;

    private final Type typeArg;

    @Override
    public Class typeArgClazz() {
        return (Class) typeArg;
    }


    public DefaultPromise() {
        this.typeArg = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean isSuccess() {
        Object result = this.result;
        return result != null && !(result instanceof CauseHolder);
    }

    @Override
    public boolean isCancellable() {
        //还没有返回结果就可以取消
        return result == null;
    }

    @Override
    public Throwable cause() {
        Object result = this.result;
        return (result instanceof CauseHolder) ? ((CauseHolder) result).cause : null;
    }

    @Override
    public DefaultPromise<V> addListener(GenericXFutureListener<? extends XFuture<? super V>> listener) {
        checkNotNull(listener, "listener");

        synchronized (this) {
            addListener0(listener);
        }

        if (isDone()) {
            notifyListeners();
        }

        return this;
    }

    @Override
    public DefaultPromise<V> addListeners(GenericXFutureListener<? extends XFuture<? super V>>... listeners) {
        checkNotNull(listeners, "listeners");

        synchronized (this) {
            for (GenericXFutureListener<? extends XFuture<? super V>> listener : listeners) {
                if (listener == null) {
                    break;
                }
                addListener0(listener);
            }
        }

        if (isDone()) {
            notifyListeners();
        }

        return this;
    }


    @Override
    public DefaultPromise<V> removeListener(GenericXFutureListener<? extends XFuture<? super V>> listener) {
        checkNotNull(listener, "listener");

        synchronized (this) {
            removeListener0(listener);
        }

        return this;
    }

    @Override
    public final DefaultPromise<V> removeListeners(GenericXFutureListener<? extends XFuture<? super V>>... listeners) {
        checkNotNull(listeners, "listeners");

        synchronized (this) {
            for (GenericXFutureListener<? extends XFuture<? super V>> listener : listeners) {
                if (listener == null) {
                    break;
                }
                removeListener0(listener);
            }
        }

        return this;
    }


    @Override
    public V getNow() {
        Object result = this.result;
        if (result instanceof CauseHolder) {
            return null;
        }
        return (V) result;
    }

    @Override
    public XFuture<V> await() throws InterruptedException {
        if (isDone()) {// 若已完成就直接返回了
            return this;
        }

        if (Thread.interrupted()) {
            throw new InterruptedException(toString());
        }

        boolean interrupted = false;//线程未中断
        synchronized (this) {
            while (!isDone()) {
                incWaiters();
                try {
                    wait();
                } catch (InterruptedException e) {
                    // 等待的时候线程中断了
                    interrupted = true;
                } finally {
                    decWaiters();
                }
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return this;


    }

    @Override
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return await0(unit.toNanos(timeout));
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (this) {
            if (isDone()) {//已完成不能取消
                return false;
            }
            RESULT_UPDATER.compareAndSet(this, this.result, new CauseHolder(new CancellationException()));
            checkNotifyWaiters();
        }
        notifyListeners(); // 通知监听器该异步操作已完成
        return true;
    }


    @Override
    public boolean isCancelled() {
        return isCancelled0(result);
    }


    @Override
    public boolean isDone() {
        return result != null;
    }


    @Override
    public Promise<V> setSuccess(V result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    @Override
    public boolean trySuccess(V result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return true;
        }
        return false;
    }

    @Override
    public Promise<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, cause);
    }

    @Override
    public boolean tryFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return true;
        }
        return false;
    }


    private boolean setSuccess0(V result) {
        if (result == null) {
            throw new NullPointerException("result can not be null");
        }
        return setValue0(result);
    }

    private boolean setFailure0(Throwable cause) {
        return setValue0(new CauseHolder(checkNotNull(cause, "cause")));
    }

    private boolean setValue0(Object objResult) {
        if (RESULT_UPDATER.compareAndSet(this, this.result, objResult)) {
            checkNotifyWaiters();
            return true;
        }
        return false;
    }


    private void addListener0(GenericXFutureListener<? extends XFuture<? super V>> listener) {
        if (listeners == null) {
            listeners = listener;
        } else if (listeners instanceof DefaultXFutureListeners) {
            ((DefaultXFutureListeners) listeners).add(listener);
        } else {
            if (listeners instanceof GenericXFutureListener) {
                listeners = new DefaultXFutureListeners((GenericXFutureListener<? extends XFuture<?>>) listeners, listener);
            }

        }
    }

    private void notifyListeners() {
        Object listeners;
        synchronized (this) {
            // 只有 有listeners并且 listeners 没有处于正在通知的时候才进行通知.
            if (notifyingListeners || this.listeners == null) {
                return;
            }
            notifyingListeners = true;
            listeners = this.listeners;
            this.listeners = null;
        }


        /*
         * 这么做的原因是 可能通知的时候还有listener添加进来持续进行通知操作
         * 知道全部都通知完
         */
        for (; ; ) {
            if (listeners instanceof DefaultXFutureListeners) {
                notifyListeners0((DefaultXFutureListeners) listeners);
            } else {
                notifyListener0(this, (GenericXFutureListener<?>) listeners);
            }

            /*
             * 前一轮通知完成后确认是否还有新的通知任务
             */
            synchronized (this) {
                if (this.listeners == null) {
                    notifyingListeners = false;
                    return;
                }
                listeners = this.listeners;
                this.listeners = null;
            }


        }


    }

    private void notifyListeners0(DefaultXFutureListeners listeners) {
        GenericXFutureListener<?>[] a = listeners.listeners();
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            notifyListener0(this, a[i]);
        }

    }

    private static void notifyListener0(XFuture future, GenericXFutureListener listener) {
        try {
            listener.operationComplete(future);
        } catch (Throwable t) {//TODO 异常输出
            System.err.print("An exception was thrown by " + listener.getClass().getName() + ".operationComplete()");
        }
    }


    private void removeListener0(GenericXFutureListener<? extends XFuture<? super V>> listener) {
        if (listeners instanceof DefaultXFutureListeners) {
            ((DefaultXFutureListeners) listeners).remove(listener);
        } else if (listeners == listener) {
            listeners = null;
        }
    }


    private boolean await0(long timeoutNanos) throws InterruptedException {
        if (isDone()) {
            return true;
        }

        if (timeoutNanos <= 0) {
            return isDone();
        }

        //返回线程的上次的中断状态，并清除中断状态 并抛出中断异常
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }


        long startTime = System.nanoTime();
        long waitTime = timeoutNanos;
        boolean interrupted = false;
        try {
            for (; ; ) {
                synchronized (this) {
                    if (isDone()) {
                        return true;
                    }
                    incWaiters();
                    try {
                        wait(waitTime / 1000000, (int) (waitTime % 1000000));
                    } catch (InterruptedException e) {
                        //说明线程已经被中断
                        interrupted = true;
                    } finally {
                        decWaiters();
                    }
                }
                if (isDone()) {
                    return true;
                } else {
                    waitTime = timeoutNanos - (System.nanoTime() - startTime);
                    if (waitTime <= 0) {
                        return isDone();
                    }
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void checkNotifyWaiters() {
        if (waiters > 0) {// 如果等待存在等待者 通知等待在该对象的wait()的线程
            notifyAll();
        }
    }

    /**
     * 如果是CancellationException 则取消成功
     */
    private static boolean isCancelled0(Object result) {
        return result instanceof CauseHolder && ((CauseHolder) result).cause instanceof CancellationException;
    }

    /**
     * 线程等待数加1
     */
    private void incWaiters() {
        if (waiters == WAITERS_MAX) {
            throw new IllegalStateException("too many waiters: " + this);
        }
        ++waiters;
    }

    /**
     * 线程等待数减1
     */
    private void decWaiters() {
        --waiters;
    }


    /**
     * 异常保存
     */
    private static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable cause) {
            this.cause = cause;
        }
    }
}

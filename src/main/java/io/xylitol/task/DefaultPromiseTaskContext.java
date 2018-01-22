package io.xylitol.task;

import io.xylitol.util.concurrent.Promise;
import io.xylitol.util.concurrent.XFuture;

/**
 * Created on 2018/1/22.
 * <p>
 * V 返回结果
 *
 * @author xuyandong
 */
public class DefaultPromiseTaskContext<V> implements Task, TaskContext<V> {

    private final TaskId id;
    private Promise<V> promise = null;
    private final Class clazz;
    private final String methodName;
    private final Object[] args;

    public DefaultPromiseTaskContext(Class clazz, String methodName, Object[] args) {
        this.id = newId();
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
    }

    public DefaultPromiseTaskContext(Class clazz, String methodName, Object[] args, Promise<V> promise) {
        this.id = newId();
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
        this.promise = promise;
    }

    public DefaultPromiseTaskContext(TaskId id, Class clazz, String methodName, Object[] args, Promise<V> promise) {
        this.id = id;
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
        this.promise = promise;
    }

    @Override
    public TaskId id() {
        return id;
    }


    public Class getClazz() {
        return clazz;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    protected TaskId newId() {
        return DefaultTaskId.newInstance();
    }

    public XFuture<V> setSuccess(V result) {
        if (promise != null) {
            promise.setSuccess(result);
        }
        return promise;

    }


    public boolean trySuccess(V result) {
        return promise != null && promise.trySuccess(result);

    }


    public XFuture<V> setFailure(Throwable cause) {
        if (promise != null) {
            promise.setFailure(cause);
        }
        return promise;
    }


    public boolean tryFailure(Throwable cause) {
        return promise != null && promise.tryFailure(cause);
    }

}

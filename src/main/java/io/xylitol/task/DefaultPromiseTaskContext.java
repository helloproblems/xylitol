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
    private Promise<V> promise;
    private final Class clazz;
    private final String methodName;
    private final Object[] args;

    public DefaultPromiseTaskContext(Promise<V> promise, Class clazz, String methodName, Object[] args) {
        this.promise = promise;
        this.id = newId();
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
    }

    public DefaultPromiseTaskContext(TaskId id, Promise<V> promise, Class clazz, String methodName, Object[] args) {
        this.promise = promise;
        this.id = id;
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
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
        promise.setSuccess(result);
        return promise;

    }


    public boolean trySuccess(V result) {
        return promise.trySuccess(result);

    }


    public XFuture<V> setFailure(Throwable cause) {
        promise.setFailure(cause);
        return promise;
    }


    public boolean tryFailure(Throwable cause) {
        return promise.tryFailure(cause);
    }

}

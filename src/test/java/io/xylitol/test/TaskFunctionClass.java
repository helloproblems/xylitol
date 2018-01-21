package io.xylitol.test;


import io.xylitol.util.concurrent.Promise;

/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public class TaskFunctionClass<T, E> {


    private Class clazz;
    private String methodName;
    private final Object[] args;
    private TaskInstance<T> taskInstance;

    private Promise<E> promise;

    public TaskFunctionClass(Class clazz, String methodName, final Object[] args, TaskInstance<T> taskInstance, Promise<E> promise) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
        this.taskInstance = taskInstance;
        this.promise = promise;
    }

    public Promise<E> getPromise() {
        return promise;
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

    public TaskInstance<T> getTaskInstance() {
        return taskInstance;
    }
}

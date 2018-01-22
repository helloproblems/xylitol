package io.xylitol.util.concurrent;

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

    public void setValue(E result) {
        this.promise.setSuccess(result);
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


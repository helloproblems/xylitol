package io.xylitol.test;


/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public class TaskFunctionClass<T> {


    private Class clazz;
    private String methodName;
    private final Object[] args;

    private TaskInstance<T> taskInstance;

    public TaskFunctionClass(Class clazz, String methodName, final Object[] args, TaskInstance<T> taskInstance) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.args = args;
        this.taskInstance = taskInstance;
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

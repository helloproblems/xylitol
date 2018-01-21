package io.xylitol.test;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public class TaskFunction {


    private BlockingQueue<TaskFunctionClass> classBlockingDeque;


    public TaskFunction() {
        this.classBlockingDeque = new LinkedBlockingQueue<>(Short.MAX_VALUE);
    }

    public TaskFunction(int capacity) {
        this.classBlockingDeque = new LinkedBlockingQueue<>(capacity);
    }

    /**
     * 添加待执行的任务
     * 如果添加失败return {@code false}
     * 成功return {@code true}
     */
    public boolean offerFunction(TaskFunctionClass taskFunctionClass) {
        return classBlockingDeque.offer(taskFunctionClass);
    }

    //开始工作
    public void work() {
        for (; ; ) {
            TaskFunctionClass taskFunctionClass;
            try {
                taskFunctionClass = classBlockingDeque.take();

                Method method = null;
                try {
                    method = getMethod(taskFunctionClass.getClazz(), taskFunctionClass);
                } catch (NoSuchMethodException e) {
                    //TODO
                    e.printStackTrace();
                }

                try {
                    int result = 0;
                    try {


//                    try {
                        TaskInstance taskInstance = taskFunctionClass.getTaskInstance();
                        result = (int) method.invoke(taskInstance.getInstance(taskFunctionClass.getClazz()), taskFunctionClass.getArgs());
//                        result = (int) method.invoke(taskFunctionClass.getClazz().newInstance(), taskFunctionClass.getArgs());
//                    } catch (InstantiationException e) {
//                        e.printStackTrace();
//                    }
                    } catch (InvocationTargetException e) {
                        //TODO
                        e.printStackTrace();
                    }
                    System.out.print(result);
                } catch (IllegalAccessException e) {
                    //TODO
                    e.printStackTrace();
                }

            } catch (InterruptedException e) {
                try {
                    wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();//TODO
                }
            }
        }


    }

    /**
     * 在所有方法中筛选出需要执行的方法
     *
     * @param workClazz
     * @param taskFunctionClass
     */
    private Method getMethod(Class workClazz, TaskFunctionClass taskFunctionClass) throws NoSuchMethodException {

        Method[] methods = workClazz.getDeclaredMethods();

        for (Method method : methods) {
            //TODO 筛选还不健全 需要确认的有:方法名、参数个数、 参数一一对应, 目前参数一一对应没有处理
            if (method.getName().equals(taskFunctionClass.getMethodName()) && method.getParameterCount() == (taskFunctionClass.getArgs().length)) {

                return method;
            }

        }
        throw new NoSuchMethodException();
    }
}

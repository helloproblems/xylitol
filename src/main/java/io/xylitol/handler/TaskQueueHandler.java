package io.xylitol.handler;

import io.xylitol.task.TaskContext;
import io.xylitol.util.exception.ReturnTypeInconsistentException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static io.xylitol.util.internal.ObjectUtil.classTypeEquals;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public class TaskQueueHandler implements TaskHandler {

    private BlockingQueue<TaskContext> taskBlockingQueue;

    public TaskQueueHandler(int capacity) {
        this.taskBlockingQueue = new LinkedBlockingQueue<>(capacity);
    }

    public TaskQueueHandler() {
        this.taskBlockingQueue = new LinkedBlockingQueue<>(Short.MAX_VALUE);
    }


    @Override
    public void taskAdded(TaskContext ctx) throws Exception {
        taskBlockingQueue.add(ctx);

    }

    @Override
    public void start() {
        for (; ; ) {
            TaskContext taskContext;
            Method method = null;
            Object result = null;
            try {
                taskContext = taskBlockingQueue.take();
                method = getMethod(taskContext);
                //TODO
                result = method.invoke(taskContext.getClazz().newInstance(), taskContext.getArgs());
                if (classTypeEquals(result.getClass(), method.getReturnType())) {
                    taskContext.setSuccess(result);
                } else {
                    exceptionCaught(taskContext, new ReturnTypeInconsistentException().getCause());
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                //TODO
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                //TODO
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                //TODO
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception eMax) {
                eMax.printStackTrace();
            }
        }

    }

    @Override
    public void exceptionCaught(TaskContext ctx, Throwable cause) throws Exception {


    }

    /**
     * 在所有方法中筛选出需要执行的方法
     *
     * @param taskContext
     */
    private Method getMethod(TaskContext taskContext) throws NoSuchMethodException {

        Method[] methods = taskContext.getClazz().getDeclaredMethods();

        for (Method method : methods) {
            //TODO 筛选还不健全 需要确认的有:方法名、参数个数、 参数一一对应, 目前参数一一对应没有处理
            if (method.getName().equals(taskContext.getMethodName()) && method.getParameterCount() == (taskContext.getArgs().length)) {
                return method;
            }

        }
        throw new NoSuchMethodException();
    }
}

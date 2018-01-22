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
public class TaskQueueHandler extends AbstractTaskHandler {

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
                result = method.invoke(taskContext.getClazz().newInstance(), taskContext.getArgs());
                //TODO 方法返回值 future的泛型 需要对应  目前没找到方法
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
        //TODO
    }


}

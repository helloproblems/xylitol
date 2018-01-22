package io.xylitol.test;


import io.xylitol.handler.TaskQueueHandler;
import io.xylitol.task.DefaultPromiseTaskContext;
import io.xylitol.task.TaskContext;
import io.xylitol.util.concurrent.Promise;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class Adder {
    public static void main(String[] args) throws Exception {
        Promise futureTest = new AdditionTestFuture().addListener(future -> System.out.println(future.get()));

        TaskQueueHandler taskQueueHandler = new TaskQueueHandler();
        TaskContext taskContext = new DefaultPromiseTaskContext(futureTest, Adder.class, "add", new Object[]{1, 2});
        taskQueueHandler.taskAdded(taskContext);
        taskQueueHandler.start();

    }

    public int add(int a, int b) {
        return a + b;
    }

}

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
        Promise<Integer> futureTest_1 = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the first " + future.get()));
        Promise<Integer> futureTest_2 = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the second " + future.get()));


        TaskQueueHandler taskQueueHandler = new TaskQueueHandler();
        TaskContext taskContext = new DefaultPromiseTaskContext<>(Adder.class, "add", new Object[]{1, 2}, futureTest_1);
        TaskContext _taskContext = new DefaultPromiseTaskContext<>(Adder.class, "add", new Object[]{5, 2}, futureTest_2);
        taskQueueHandler.taskAdded(taskContext);
        taskQueueHandler.taskAdded(_taskContext);
        taskQueueHandler.start();
    }

    public int add(int a, int b) {
        return a + b;
    }

}

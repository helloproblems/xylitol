package io.xylitol.test;


import io.xylitol.handler.TaskQueueHandler;
import io.xylitol.task.DefaultPromiseTaskContext;
import io.xylitol.task.TaskContext;
import io.xylitol.util.concurrent.DefaultPromise;
import io.xylitol.util.concurrent.Promise;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class Adder {
    public static void main(String[] args) throws Exception {
        Promise<Integer> futureTest_1 = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the add int " + future.get()));
        Promise<Long> futureTest_2 = new DefaultPromise() {
        }.addListener(future -> System.out.println("hello I'm the add long " + future.get()));
        Promise<Integer> futureTest_3 = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the multiply " + future.get()));


        TaskQueueHandler taskQueueHandler = new TaskQueueHandler();
        TaskContext taskContext_1 = new DefaultPromiseTaskContext<>(Adder.class, "add", new Object[]{3, 7L}, futureTest_1);
        TaskContext taskContext_2 = new DefaultPromiseTaskContext<>(Adder.class, "add", new Object[]{3, 7L}, futureTest_2);
        TaskContext taskContext_3 = new DefaultPromiseTaskContext<>(Adder.class, "multiply", new Object[]{5, 12}, futureTest_3);
        taskQueueHandler.taskAdded(taskContext_1);
        taskQueueHandler.taskAdded(taskContext_2);
        taskQueueHandler.taskAdded(taskContext_3);
        taskQueueHandler.start();
    }

    public int add(int a, int b) {
        return a + b;
    }

    public long add(int a, long b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }


}

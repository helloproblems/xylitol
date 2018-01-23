# xylitol
一个异步的工具类

使用方法

    public static void main(String[] args) {
        Promise<Integer> futureTest_1 = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the add int " + future.get()));
        DefaultPromise<Long> futureTest_2 = new DefaultPromise<Long>() {
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
    

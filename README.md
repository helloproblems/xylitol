# xylitol
一个异步的工具类

使用方法

    public static void main(String[] args) {
        Promise futureTest = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the first " + future.get()));
        Promise _futureTest = new AdditionTestFuture().addListener(future -> System.out.println("hello I'm the second " + future.get()));
        TaskQueueHandler taskQueueHandler = new TaskQueueHandler();

        TaskContext taskContext = new DefaultPromiseTaskContext(Adder.class, "add", new Object[]{1, 2}, futureTest);

        TaskContext _taskContext = new DefaultPromiseTaskContext(Adder.class, "add", new Object[]{5, 2}, _futureTest);
        taskQueueHandler.taskAdded(taskContext);
        taskQueueHandler.taskAdded(_taskContext);
        taskQueueHandler.start();

    }
    

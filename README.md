# xylitol

使用方法

    public static void main(String[] args) {
        AdditionTestFuture future = (AdditionTestFuture) new AdditionTestFuture().addListener((GenericXFutureListener<Promise<Integer>>) future12 -> System.out.println(future12.getNow()));

        AdditionTestFuture _future = (AdditionTestFuture) new AdditionTestFuture().addListener((GenericXFutureListener<Promise<Integer>>) future1 -> System.out.println(future1.getNow()));

        TaskFunctionClass taskFunctionClass1 = new TaskFunctionClass(Adder.class, "add", new Object[]{1, 2}, new DefaultTaskInstance<Adder>(), future);

        TaskFunctionClass taskFunctionClass2 = new TaskFunctionClass(Adder.class, "add", new Object[]{2, 3}, new DefaultTaskInstance<Adder>(), _future);

        TaskFunction taskFunction = new TaskFunction();


        taskFunction.offerFunction(taskFunctionClass1);
        taskFunction.offerFunction(taskFunctionClass2);


        taskFunction.work();

    }
    

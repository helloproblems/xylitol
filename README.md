# xylitol

使用方法

    ```
    public static void main(String[] args) {

        TaskFunctionClass taskFunctionClass = new TaskFunctionClass(Adder.class, "add", new Object[]{1, 2}, new DefaultTaskInstance<Adder>());

        TaskFunction taskFunction = new TaskFunction();


        taskFunction.offerFunction(taskFunctionClass);

        taskFunction.work();

    }
    
    ```

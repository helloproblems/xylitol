package io.xylitol.test;


import io.xylitol.util.concurrent.DefaultTaskInstance;
import io.xylitol.util.concurrent.TaskFunction;
import io.xylitol.util.concurrent.TaskFunctionClass;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class Adder {
    public static void main(String[] args) {
        TaskFunction taskFunction = new TaskFunction();


        AdditionTestFuture future = (AdditionTestFuture) new AdditionTestFuture().addListener(future1 -> System.out.println(future1.getNow()));
        AdditionTestFuture _future = (AdditionTestFuture) new AdditionTestFuture().addListener(future1 -> System.out.println(future1.getNow()));

        TaskFunctionClass taskFunctionClass1 = new TaskFunctionClass<>(Adder.class, "add", new Object[]{1, 2}, new DefaultTaskInstance<>(), future);
        TaskFunctionClass taskFunctionClass2 = new TaskFunctionClass<Adder, Integer>(Adder.class, "add", new Object[]{2, 3}, new DefaultTaskInstance<>(), _future);


        taskFunction.offerFunction(taskFunctionClass1);
        taskFunction.offerFunction(taskFunctionClass2);
        taskFunction.work();

    }

    public int add(int a, int b) {
        return a + b;
    }

}

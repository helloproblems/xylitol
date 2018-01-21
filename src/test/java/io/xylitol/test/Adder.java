package io.xylitol.test;


import io.xylitol.util.concurrent.GenericXFutureListener;
import io.xylitol.util.concurrent.Promise;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class Adder {
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

    public int add(int a, int b) {
        return a * b;
    }
//
//    public AdditionTestFuture add(long delay, int a, int b) {
//        AdditionTestFuture future = new AdditionTestFuture();
//        new Thread(new DelayAdditionTask(delay, a, b, future)).start();
//        return future;
//    }
//
//    private class DelayAdditionTask implements Runnable {
//
//        private long delay;
//
//        private int a, b;
//
//        private AdditionTestFuture future;
//
//        public DelayAdditionTask(long delay, int a, int b, AdditionTestFuture future) {
//            super();
//            this.delay = delay;
//            this.a = a;
//            this.b = b;
//            this.future = future;
//        }
//
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(delay);
//                Integer i = a + b;
//                // TODO 这里设置future为完成状态(正常执行完毕)
//                future.setSuccess(i);
//            } catch (InterruptedException e) {
//                // TODO 这里设置future为完成状态(异常执行完毕)
//                future.setFailure(e.getCause());
//            }
//        }
//
//    }
}

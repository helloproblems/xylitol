package io.xylitol.test;


/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class Adder {
    public static void main(String[] args) {

        TaskFunctionClass taskFunctionClass = new TaskFunctionClass(Adder.class, "add", new Object[]{1, 2}, new DefaultTaskInstance<Adder>());

        TaskFunction taskFunction = new TaskFunction();


        taskFunction.offerFunction(taskFunctionClass);

        taskFunction.work();
//        new Adder().add(3 * 1000, 1, 2).addListener(new GenericXFutureListener<XFuture<? super Integer>>() {
//            @Override
//            public void operationComplete(XFuture<? super Integer> future) throws Exception {
//                System.out.print(future.get());
//            }
//        });
    }

    public int add(int a, int b) {
        return a + b;
    }

    public AdditionTestFuture add(long delay, int a, int b) {
        AdditionTestFuture future = new AdditionTestFuture();
        new Thread(new DelayAdditionTask(delay, a, b, future)).start();
        return future;
    }

    private class DelayAdditionTask implements Runnable {

        private long delay;

        private int a, b;

        private AdditionTestFuture future;

        public DelayAdditionTask(long delay, int a, int b, AdditionTestFuture future) {
            super();
            this.delay = delay;
            this.a = a;
            this.b = b;
            this.future = future;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delay);
                Integer i = a + b;
                // TODO 这里设置future为完成状态(正常执行完毕)
                future.setSuccess(i);
            } catch (InterruptedException e) {
                // TODO 这里设置future为完成状态(异常执行完毕)
                future.setFailure(e.getCause());
            }
        }

    }
}

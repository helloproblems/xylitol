package io.xylitol.test;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class Adder {
    public static void main(String[] args) {

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

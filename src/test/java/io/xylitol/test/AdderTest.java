package io.xylitol.test;

import io.xylitol.util.concurrent.GenericXFutureListener;
import io.xylitol.util.concurrent.XFuture;
import org.junit.Test;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class AdderTest {

    @Test
    public void adderTest() {
        new Adder().add(3 * 1000, 1, 2).addListener(new GenericXFutureListener<XFuture<? super Integer>>() {
            @Override
            public void operationComplete(XFuture<? super Integer> future) throws Exception {
                System.out.print(future.get());
            }
        });
    }
}

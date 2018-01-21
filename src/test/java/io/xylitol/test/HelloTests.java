package io.xylitol.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public class HelloTests {
    @Test
    public void test1() {
        int size = 11;
        int dSize = size << 1;
        System.out.print(dSize);
        Assert.assertSame(2 * size, dSize);
    }
}

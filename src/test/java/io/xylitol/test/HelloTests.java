package io.xylitol.test;

import io.xylitol.util.concurrent.TaskFunction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void test2() {
//        List<TaskFunction> functions = new ArrayList<>();
//        functions.add(() -> 1 + 2);
//        System.out.print(functions.size());
    }
}

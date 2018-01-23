package io.xylitol.test;

import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


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
    public void testType() {
        Type t = new Base<Map<String, List<String>>>() {
        }.getClass().getGenericSuperclass();

        Type targ = ((ParameterizedType) t).getActualTypeArguments()[0];
        System.out.println(targ.toString());
        System.out.println(((ParameterizedTypeImpl) targ).getRawType());

    }

}

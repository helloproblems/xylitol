package io.xylitol.test;

import org.junit.Assert;
import org.junit.Test;
import sun.nio.ch.ThreadPool;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;


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

       int i = (1 - 123) & dSize;
        Assert.assertSame(2 * size, dSize);
    }

    @Test
    public void testType() {
        Type t = new Base<Map<String, List<String>>>() {
        }.getClass().getGenericSuperclass();

        Type targ = ((ParameterizedType) t).getActualTypeArguments()[0];
        System.out.println(targ.toString());
        HashMap map = new HashMap();
//        ThreadPool
        System.out.println(((ParameterizedTypeImpl) targ).getRawType());

    }

    @Test
    public void singletonTest(){
        SingletonDemo.INSTANCE.otherMethods().otherMethods().otherMethods();
    }

}

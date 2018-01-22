package io.xylitol.util.internal;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class ObjectUtil {
    public static <T> T checkNotNull(T arg, String text) {
        if (arg == null) {
            throw new NullPointerException(text);
        }
        return arg;
    }

    //基础数据类型装箱升级
    public static boolean classTypeEquals(Class clazz1, Class clazz2) {
        if (clazz1 == null || clazz2 == null) {
            throw new NullPointerException("method classTypeEquals one of the args is null");
        }
        if (clazz1.isPrimitive() == clazz2.isPrimitive()) {//同为原始数据类型或者同为非原始数据类型
            return clazz1.equals(clazz2);
        }
        return PrimitiveBoxingEnum.getByClassName(clazz1.getName()) == PrimitiveBoxingEnum.getByClassName(clazz2.getName());
    }
}

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
}

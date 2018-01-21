package io.xylitol.util.concurrent;

/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public class DefaultTaskInstance<T> implements TaskInstance<T> {
    @Override
    public T getInstance(String instanceName) {
        return null;
    }

    @Override
    public T getInstance(Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

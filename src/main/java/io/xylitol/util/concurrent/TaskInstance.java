package io.xylitol.util.concurrent;

/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public interface TaskInstance<T> {

    T getInstance(String instanceName);

    T getInstance(Class clazz);

}
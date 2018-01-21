package io.xylitol.test;

/**
 * Created on 2018/1/21.
 *
 * @author xuyandong
 */
public interface TaskInstance<T> {

    T getInstance(String instanceName);

    T getInstance(Class clazz);

}

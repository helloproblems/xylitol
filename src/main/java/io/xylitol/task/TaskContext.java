package io.xylitol.task;

import io.xylitol.util.concurrent.Valid;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public interface TaskContext<V> extends Valid<V> {

    Class getClazz();

    String getMethodName();

    Object[] getArgs();

}

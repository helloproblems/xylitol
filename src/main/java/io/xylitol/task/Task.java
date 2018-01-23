package io.xylitol.task;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public interface Task<V> {

    /**
     * 返回 TaskId
     */
    TaskId id();

    boolean resultCheck(V result);

}

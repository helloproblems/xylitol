package io.xylitol.handler;

import io.xylitol.task.TaskContext;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public interface TaskHandler {

    /**
     * 添加任务
     */
    void taskAdded(TaskContext ctx) throws Exception;

    /**
     * 处理开始
     */
    void start();

    /**
     * 捕捉异常 如果有异常抛出
     */
    void exceptionCaught(TaskContext ctx, Throwable cause) throws Exception;
}

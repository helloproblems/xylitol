package io.xylitol.task;

import java.io.Serializable;

/**
 * Created on 2018/1/22.
 * <p>
 * 任务ID
 *
 * @author xuyandong
 */
public interface TaskId extends Serializable, Comparable<TaskId> {
    /**
     * 返回 task 全局非唯一的短ID {@link TaskId}.
     */
    String asShortText();

    /**
     * 返回 task 全局唯一的长ID {@link TaskId}.
     */
    String asLongText();
}

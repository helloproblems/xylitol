package io.xylitol.task;

/**
 * Created on 2018/1/22.
 * <p>
 * TODO
 *
 * @author xuyandong
 */
public class DefaultTaskId implements TaskId {

    private transient String shortValue = "OK";
    private transient String longValue = "ok";

    /**
     * 返回一个新的 {@link DefaultTaskId} 实例.
     */
    public static DefaultTaskId newInstance() {
        return new DefaultTaskId();
    }


    @Override
    public String asShortText() {
        return shortValue;
    }

    @Override
    public String asLongText() {
        return longValue;
    }

    @Override
    public int compareTo(TaskId o) {
        return 0;
    }
}

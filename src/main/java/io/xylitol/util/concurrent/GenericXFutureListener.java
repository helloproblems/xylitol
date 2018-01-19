package io.xylitol.util.concurrent;

import java.util.EventListener;

/**
 * Created on 2018/1/18.
 * <p>
 * 通用的XFutureListener
 *
 * @author xuyandong
 */
public interface GenericXFutureListener<F extends XFuture<?>> extends EventListener {

    /**
     * 操作完成后执行的逻辑
     */
    void operationComplete(F future) throws Exception;
}

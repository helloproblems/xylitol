package io.xylitol.test;

import io.xylitol.util.concurrent.DefaultPromise;
import io.xylitol.util.concurrent.Promise;

/**
 * Created on 2018/1/19.
 *
 * @author xuyandong
 */
public class AdditionTestFuture extends DefaultPromise<Integer> {


    @Override
    public Promise<Integer> setSuccess(Integer result) {
        return super.setSuccess(result);
    }

    @Override
    public Promise<Integer> setFailure(Throwable cause) {
        return super.setFailure(cause);
    }
}

package io.xylitol.util.exception;

/**
 * Created on 2018/1/22.
 * <p>
 * 返回类型与接收类型不一致异常
 *
 * @author xuyandong
 */
public class ReturnTypeInconsistentException extends RuntimeException {

    private static final long serialVersionUID = 315340046263452299L;

    public ReturnTypeInconsistentException() {
        super();
    }

    public ReturnTypeInconsistentException(String message) {
        super(message);
    }
}

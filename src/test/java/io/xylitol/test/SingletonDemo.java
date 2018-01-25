package io.xylitol.test;

/**
 * Created on 2018/1/25.
 *
 * @author xuyandong
 */
public enum SingletonDemo {

    INSTANCE;

    public SingletonDemo otherMethods() {
        System.out.println("Something");
        return this;
    }
}

package io.xylitol.util.internal;

import java.util.NoSuchElementException;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public enum PrimitiveBoxingEnum {
    INT("int", "java.lang.Integer"),
    LONG("long", "java.lang.Long"),
    FLOAT("float", "java.lang.Float"),
    DOUBLE("double", "java.lang.Double"),
    BYTE("byte", "java.lang.Byte"),
    CHAR("char", "java.lang.Char"),
    SHORT("short", "java.lang.Short"),
    BOOLEAN("boolean", "java.lang.Boolean");

    private String unBoxingName;
    private String boxingName;

    PrimitiveBoxingEnum(String unBoxingName, String boxingName) {
        this.unBoxingName = unBoxingName;
        this.boxingName = boxingName;
    }

    public String getUnBoxingName() {
        return unBoxingName;
    }

    public String getBoxingName() {
        return boxingName;
    }

    public static PrimitiveBoxingEnum getByUnBoxingName(String unBoxingName) {
        return PrimitiveBoxingEnum.valueOf(unBoxingName.toUpperCase());
    }


    public static PrimitiveBoxingEnum getByClassName(String className) {
        for (PrimitiveBoxingEnum boxingEnum : PrimitiveBoxingEnum.values()) {
            if (boxingEnum.boxingName.equals(className) || boxingEnum.unBoxingName.equals(className)) {
                return boxingEnum;
            }
        }
        throw new NoSuchElementException(className);
    }

}

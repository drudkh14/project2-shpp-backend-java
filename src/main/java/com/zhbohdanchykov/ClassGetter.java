package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassGetter {

    private final String className;

    public static final Logger logger = LoggerFactory.getLogger(ClassGetter.class);

    public ClassGetter(String className) {
        this.className = className;
        logger.info("Created ClassGetter class. Got class name {}.", className);
    }

    public Class<? extends Number> getNumericClass() throws IllegalArgumentException {
        return switch (className) {
            case "int" -> Integer.class;
            case "byte" -> Byte.class;
            case "short" -> Short.class;
            case "long" -> Long.class;
            case "float" -> Float.class;
            case "double" -> Double.class;
            default -> throw new IllegalArgumentException("Unexpected type for multiplication table: " + className +
                    ". You should use numeric types.");
        };
    }
}

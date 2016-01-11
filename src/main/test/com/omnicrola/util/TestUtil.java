package com.omnicrola.util;

import junit.framework.AssertionFailedError;

/**
 * Created by omnic on 1/10/2016.
 */
public class TestUtil {
    public static <T> T assertIsOfType(Class<T> targetClass, Object actual) {
        if (actual == null) {
            throw new AssertionFailedError("Object was null, instead of class '" + targetClass.getName() + "'.");
        }
        if (actual.getClass().equals(targetClass)) {
            return (T) actual;
        }
        throw new AssertionFailedError("Class was not of type '" + targetClass.getName() + "', was instead '" + actual.getClass().getName() + "'");
    }

}

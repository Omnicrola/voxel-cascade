package com.omnicrola.util;

import junit.framework.AssertionFailedError;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by omnic on 1/15/2016.
 */
public abstract class TestTools {

    public static <T> T assertIsOfType(Class<T> targetClass, Object actual) {
        if (actual == null) {
            throw new AssertionFailedError("Object was null, instead of class '" + targetClass.getName() + "'.");
        }
        if (actual.getClass().equals(targetClass)) {
            return (T) actual;
        }
        throw new AssertionFailedError("Class was not of type '" + targetClass.getName() + "', was instead '" + actual.getClass().getName() + "'");
    }

    public static <T> T verifyDependency(Class<T> dependancyClass, String dependancyName, Object targetObject) {
        Field field = findDependencyField(dependancyName, targetObject.getClass());
        Object value = getFieldValue(targetObject, field);
        if (value == null) {
            throw new AssertionFailedError("Dependency '" + dependancyName + "' was null.");
        }
        if (value.getClass().equals(dependancyClass)) {
            return (T) value;
        } else {
            throw new AssertionFailedError("Dependancy '" + dependancyName +
                    "' did not have the correct class.  Expected '" +
                    dependancyClass.getSimpleName() + "' but was '" +
                    value.getClass().getSimpleName() + "'.");
        }
    }

    public static Object getFieldValue(Object targetObject, Field field) {
        try {
            field.setAccessible(true);
            return field.get(targetObject);
        } catch (IllegalAccessException e) {
            throw new AssertionFailedError("Cannot access field : " + field.getName());
        }
    }

    public static Field findDependencyField(String dependancyName, Class<?> targetClass) {
        Optional<Field> first = fieldStream(targetClass)
                .filter(f -> hasDependencyAnnotation(f))
                .filter(f -> isDependecyOfInterest(dependancyName, f))
                .findFirst();
        if (!first.isPresent()) {
            throw new AssertionFailedError("Object " + targetClass.getSimpleName() +
                    "does not have a field marked with @Dependancy named '"
                    + dependancyName + "'.");
        }
        return first.get();
    }

    private static boolean isDependecyOfInterest(String dependancyName, Field f) {
        return f.getAnnotation(Dependency.class).value().equals(dependancyName);
    }

    private static boolean hasDependencyAnnotation(Field f) {
        return f.getAnnotation(Dependency.class) != null;
    }

    private static Stream<Field> fieldStream(Class<?> targetClass) {
        return Arrays.asList(targetClass.getDeclaredFields()).stream();
    }
}

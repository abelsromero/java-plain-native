package org.abelsromero.demo.test;

import java.lang.reflect.Field;

/**
 * Required to run native tests since Mockito does not support native compilation.
 */
public class ReflectionMock<T> {

    private final Object instance;
    private final Class<T> clazz;

    private ReflectionMock(Object instance) {
        this.instance = instance;
        this.clazz = (Class<T>) instance.getClass();
    }

    public static <T> ReflectionMock<T> mock(T clazz) {
        return new ReflectionMock<>(clazz);
    }

    public void mockBoolean(String field, Boolean value) {
        setValue(field, value);
    }

    public void mockInteger(String field, Integer value) {
        setValue(field, value);
    }

    public void mockString(String field, String value) {
        setValue(field, value);
    }

    public void mockValue(String field, Object value) {
        setValue(field, value);
    }

    private void setValue(String field, Object value) {
        try {
            final Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getInstance() {
        return (T) instance;
    }

}

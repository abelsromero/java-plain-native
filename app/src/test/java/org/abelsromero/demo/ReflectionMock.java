package org.abelsromero.demo;

import java.lang.reflect.Field;

class ReflectionMock<T> {

    private final Object instance;
    private final Class<T> clazz;

    private ReflectionMock(Object instance) {
        this.instance = instance;
        this.clazz = (Class<T>) instance.getClass();
    }

    static <T> ReflectionMock<T> mock(Object clazz) {
        return new ReflectionMock<>(clazz);
    }

    void mockBoolean(String field, Boolean value) {
        try {
            final Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void mockString(String field, String value) {
        try {
            Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    T getInstance() {
        return (T) instance;
    }
}

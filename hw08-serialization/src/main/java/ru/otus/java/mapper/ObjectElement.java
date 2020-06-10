package ru.otus.java.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
public class ObjectElement {
    @Getter
    private Object mainObject;
    @Getter
    private Field[] mainFields;

    public ObjectElement(Object object) {
        this.mainObject = object;
        if (this.mainObject != null) {
            this.mainFields = getObjectFields(this.mainObject);
        }
    }

    public boolean isPrimitive(Object object) {
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        return clazz == Boolean.class || clazz == Character.class ||
                clazz == Byte.class || clazz == Short.class ||
                clazz == Integer.class || clazz == Long.class ||
                clazz == Float.class || clazz == Double.class;
    }

    Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    String getFieldName(Field field) {
        return field.getName();
    }

    public Field[] getObjectFields(Object object) {
        return object.getClass().getDeclaredFields();
    }
}

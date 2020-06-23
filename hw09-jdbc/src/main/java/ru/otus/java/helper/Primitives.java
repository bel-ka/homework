package ru.otus.java.helper;

import java.util.Map;

import static java.util.Map.entry;

public class Primitives {
    private static final Map<Object, Object> primitivesMap = Map.ofEntries(
            entry(int.class, Integer.class),
            entry(float.class, Float.class),
            entry(byte.class, Byte.class),
            entry(double.class, Double.class),
            entry(long.class, Long.class),
            entry(char.class, Character.class),
            entry(boolean.class, Boolean.class),
            entry(short.class, Short.class),
            entry(void.class, Void.class));

    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrap(Class<T> type) {
        if (primitivesMap.containsKey(type)) {
            return (Class<T>) primitivesMap.get(type);
        }
        return type;
    }
}

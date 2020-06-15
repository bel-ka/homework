package ru.otus.java.mapper;

import ru.otus.java.element.ElementFactory;
import ru.otus.java.element.JsonElement;

import java.lang.reflect.Field;

public class JsonWriter {
    private static final String OBJECT_BEGIN = "{";
    private static final String OBJECT_END = "}";
    public static final String OBJECT_SEPARATOR = ",";
    private static final String VALUE_SEPARATOR = ":";
    public static final String ARRAY_BEGIN = "[";
    public static final String ARRAY_END = "]";
    public static final String DECORATOR = "\"";
    private final StringBuilder out = new StringBuilder();
    private final ObjectElement objElem;

    public JsonWriter(ObjectElement objElem) {
        this.objElem = objElem;
    }

    public String write() {
        if (objElem.getMainObject() == null) {
            out.append((Object) null);
        } else {
            appendElementToOutput(objElem.getMainObject(), objElem.getMainFields());
        }
        return out.toString();
    }

    public void appendValueToOutput(String value){
        out.append(value);
    }

    public void appendElementToOutput(Object obj, Field[] fields) {
        out.append(OBJECT_BEGIN);
        int i = 0;
        for (Field field : fields) {
            out.append(DECORATOR).append(objElem.getFieldName(field)).append(DECORATOR).append(VALUE_SEPARATOR);
            Object fieldValue = objElem.getFieldValue(obj, field);
            ElementFactory factory = new ElementFactory(this);
            JsonElement element = factory.getType(fieldValue);
            element.appendValueToWriter();
            if (i++ != fields.length - 1) {
                out.append(OBJECT_SEPARATOR);
            }
        }
        out.append(OBJECT_END);
    }
}
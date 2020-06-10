package ru.otus.java.element;

import ru.otus.java.mapper.JsonWriter;
import ru.otus.java.mapper.ObjectElement;

import java.util.Collection;

public class ElementFactory {
    private final ObjectElement objElem = new ObjectElement();
    private final JsonWriter writer;

    public ElementFactory (JsonWriter writer){
        this.writer = writer;
    }

    public JsonElement getType(Object element) {
        if (element instanceof Collection<?>) {
            return new CollectionJsonElement(element, writer);
        } else if (objElem.isPrimitive(element)) {
            return new PrimitiveJsonElement(element, writer);
        } else if (element == null) {
            return new NullJsonElement(null, writer);
        } else {
            return new ObjectJsonElement(element, writer);
        }
    }
}
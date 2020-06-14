package ru.otus.java.element;

import ru.otus.java.mapper.JsonWriter;

public abstract class JsonElement {
    protected Object element;
    protected JsonWriter writer;

    public JsonElement(Object element, JsonWriter writer) {
        this.element = element;
        this.writer = writer;
    }

    public abstract void appendValueToWriter();
}
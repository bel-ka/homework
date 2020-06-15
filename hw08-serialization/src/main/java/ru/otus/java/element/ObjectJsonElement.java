package ru.otus.java.element;

import ru.otus.java.mapper.JsonWriter;

class ObjectJsonElement extends JsonElement {

    public ObjectJsonElement(Object fieldValue, JsonWriter writer) {
        super(fieldValue, writer);
    }

    @Override
    public void appendValueToWriter() {
        super.writer.appendValueToOutput(JsonWriter.DECORATOR + super.element.toString() + JsonWriter.DECORATOR);
    }
}

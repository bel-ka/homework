package ru.otus.java.element;

import ru.otus.java.mapper.JsonWriter;

class PrimitiveJsonElement extends JsonElement {

    public PrimitiveJsonElement(Object fieldValue, JsonWriter writer) {
        super(fieldValue, writer);
    }

    @Override
    public void appendValueToWriter() {
        super.writer.appendValueToOutput(super.element.toString());
    }
}

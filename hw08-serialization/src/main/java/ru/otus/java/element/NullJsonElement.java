package ru.otus.java.element;

import ru.otus.java.mapper.JsonWriter;

class NullJsonElement extends JsonElement {

    public NullJsonElement(Object fieldValue, JsonWriter writer) {
        super(fieldValue, writer);
    }

    @Override
    public void appendValueToWriter() {
        super.writer.appendValueToOutput(null);
    }
}

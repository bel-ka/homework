package ru.otus.java.element;

import ru.otus.java.mapper.JsonWriter;
import ru.otus.java.mapper.ObjectElement;

import java.util.Collection;

class CollectionJsonElement extends JsonElement {
    private final ObjectElement objElem = new ObjectElement();

    public CollectionJsonElement(Object fieldValue, JsonWriter writer) {
        super(fieldValue, writer);
    }

    @Override
    public void appendValueToWriter() {
        Collection<?> col = (Collection<?>) super.element;
        super.writer.appendValueToOutput(JsonWriter.ARRAY_BEGIN);
        int c = 0;
        for (Object oVal : col) {
            super.writer.appendElementToOutput(oVal, objElem.getObjectFields(oVal));
            if (c++ != col.size() - 1) {
                super.writer.appendValueToOutput(JsonWriter.OBJECT_SEPARATOR);
            }
        }
        super.writer.appendValueToOutput(JsonWriter.ARRAY_END);
    }
}

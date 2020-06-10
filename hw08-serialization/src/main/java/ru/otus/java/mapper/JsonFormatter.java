package ru.otus.java.mapper;

public class JsonFormatter {

    public String toJson(Object obj) {
        ObjectElement objElem = new ObjectElement(obj);
        JsonWriter writer = new JsonWriter(objElem);
        return writer.write();
    }
}

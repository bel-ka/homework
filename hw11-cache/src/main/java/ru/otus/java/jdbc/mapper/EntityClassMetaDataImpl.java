package ru.otus.java.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);
    private final Class<T> entityClazz;
    private final List<Field> allEntityField;
    private Field idField = null;

    public EntityClassMetaDataImpl(Class<T> object) {
        this.entityClazz = object;
        allEntityField = Stream.of(entityClazz.getDeclaredFields()).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return entityClazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return entityClazz.getConstructor();
        } catch (NoSuchMethodException e) {
            logger.debug(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Field getIdField() {
        allEntityField.forEach(field -> {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            }
        });
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allEntityField;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (idField == null) {
            getIdField();
        }
        List<Field> fieldsWithoutId = allEntityField.stream()
                .filter(field -> {
                    if (idField != null) {
                        return !field.getName().equals(idField.getName());
                    }
                    return true;
                })
                .collect(Collectors.toList());
        return fieldsWithoutId;
    }
}

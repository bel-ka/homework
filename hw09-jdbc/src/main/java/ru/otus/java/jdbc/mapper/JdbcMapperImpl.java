package ru.otus.java.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.helper.Primitives;
import ru.otus.java.jdbc.exception.JdbcMapperException;
import ru.otus.java.jdbc.executor.DbExecutorImpl;
import ru.otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);
    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<T> dbExecutor;
    EntityClassMetaData<T> classMetaData;
    EntitySQLMetaData sqlEntity;

    @SuppressWarnings("unchecked")
    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutorImpl<T> dbExecutor, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.classMetaData = new EntityClassMetaDataImpl<T>((T) clazz);
        this.sqlEntity = new EntitySQLMetaDataImpl(classMetaData);
    }

    @Override
    public void insert(T objectData) {
        List<Object> values = new ArrayList<>();
        classMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            values.add(getFieldValue(field, objectData));
        });
        sessionManager.beginSession();
        try {
            long id = dbExecutor.executeInsert(getConnection(), sqlEntity.getInsertSql(), values);
            logger.debug("insert with id = {}", id);
            sessionManager.commitSession();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        List<Object> values = new ArrayList<>();
        classMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            values.add(getFieldValue(field, objectData));
        });
        Field idField = classMetaData.getIdField();
        values.add(getFieldValue(idField, objectData));
        sessionManager.beginSession();
        try {
            long id = dbExecutor.executeInsert(getConnection(), sqlEntity.getUpdateSql(), values);
            logger.debug("update with id = {}", id);
            sessionManager.commitSession();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        Field idField = classMetaData.getIdField();
        long id = (Long) getFieldValue(idField, objectData);
        Optional<T> objectFromDb = findById(id);
        if (objectFromDb.isPresent()) {
            update(objectData);
        } else {
            insert(objectData);
        }
    }

    @Override
    public Optional<T> findById(long id) {
        try {
            return dbExecutor.executeSelect(getConnection(), sqlEntity.getSelectByIdSql(),
                    id, rs -> {
                        try {
                            if (rs.next()) {
                                T newObject = classMetaData.getConstructor().newInstance();
                                for (var field : classMetaData.getAllFields()) {
                                    var nameField = field.getName();
                                    Class<?> typeField = field.getType();
                                    field.setAccessible(true);
                                    if (typeField.isPrimitive()) {
                                        field.set(newObject, rs.getObject(nameField, Primitives.wrap(typeField)));
                                    } else {
                                        field.set(newObject, rs.getObject(nameField, typeField));
                                    }
                                }
                                return newObject;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    private Object getFieldValue(Field field, T objectData) {
        try {
            return field.get(objectData);
        } catch (IllegalAccessException e) {
            logger.debug(e.getLocalizedMessage());
            throw new JdbcMapperException(e);
        }
    }
}
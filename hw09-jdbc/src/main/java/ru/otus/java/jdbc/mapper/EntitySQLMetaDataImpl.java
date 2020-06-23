package ru.otus.java.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private static final Logger logger = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);
    private final EntityClassMetaData<?> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT ");
        StringJoiner fieldJoiner = new StringJoiner(",");
        classMetaData.getAllFields().forEach(field -> fieldJoiner.add(field.getName()));
        selectSql.append(fieldJoiner);
        selectSql.append(String.format(" FROM %s", classMetaData.getName()));
        logger.debug(selectSql.toString());
        return selectSql.toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT ");
        StringJoiner fieldJoiner = new StringJoiner(",");
        classMetaData.getAllFields().forEach(field -> fieldJoiner.add(field.getName()));
        selectSql.append(fieldJoiner);
        selectSql.append(String.format(" FROM %s where %s = ?", classMetaData.getName(), classMetaData.getIdField().getName()));
        logger.debug(selectSql.toString());
        return selectSql.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(String.format("INSERT into %s(", classMetaData.getName()));
        StringJoiner fieldJoiner = new StringJoiner(",");
        StringJoiner valueJoiner = new StringJoiner(",");
        classMetaData.getFieldsWithoutId().forEach(field -> {
            fieldJoiner.add(field.getName());
            valueJoiner.add("?");
        });
        insertSql.append(fieldJoiner);
        insertSql.append(") values (");
        insertSql.append(valueJoiner).append(")");
        logger.debug(insertSql.toString());
        return insertSql.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append(String.format("UPDATE %s SET ", classMetaData.getName()));
        StringJoiner fieldJoiner = new StringJoiner(",");
        classMetaData.getFieldsWithoutId().forEach(
                field -> fieldJoiner.add(String.format("%s = ?", field.getName()))
        );
        updateSql.append(fieldJoiner);
        updateSql.append(String.format(" WHERE %s = ?", classMetaData.getIdField().getName()));
        logger.debug(updateSql.toString());
        return updateSql.toString();
    }
}
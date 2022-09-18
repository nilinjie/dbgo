package cn.com.dbgo.core.dialect.entity;

import cn.com.dbgo.core.dialect.IDbTableColumn;

import java.io.Serializable;

/**
 * Mysql数据库表字段信息
 *
 * @author lingee
 * @date 2022/8/31
 */
public class MySqlTableColumn implements IDbTableColumn, Serializable {

    private static final long serialVersionUID = 1147940335301071456L;

    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 字段字符集
     */
    private String columnCharset;
    /**
     * 主键字段
     */
    private String columnKey;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 是否为空
     */
    private String nullable;
    /**
     * 扩展信息
     */
    private String extra;

    @Override
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getColumnType() {
        return columnType;
    }

    @Override
    public boolean isPrimaryKey() {
        return null != this.columnKey && "PRI".equals(this.columnKey);
    }

    @Override
    public boolean isDefaultNotNull() {
        return null != this.nullable && "NO".equals(this.nullable);
    }

    @Override
    public boolean isAutoIncrement() {
        return null != this.extra && "auto_increment".equals(this.extra);
    }


    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnCharset() {
        return columnCharset;
    }

    public void setColumnCharset(String columnCharset) {
        this.columnCharset = columnCharset;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

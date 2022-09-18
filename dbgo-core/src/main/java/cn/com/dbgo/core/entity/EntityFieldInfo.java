package cn.com.dbgo.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;

/**
 * 实体类映射字段信息
 *
 * @author lingee
 * @date 2022/9/2
 */
public class EntityFieldInfo {

    /**
     * 字段注释
     */
    private String comment;
    /**
     * 是否默认不为空
     */
    private boolean defaultNotNull;
    /**
     * 主键类型
     */
    private IdType idType;
    /**
     * 是否主键
     */
    private boolean primaryKey;
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 表字段类型
     */
    private String columnType;

    public EntityFieldInfo() {
    }

    public EntityFieldInfo(TableFieldInfo tableFieldInfo) {
        this.columnName = tableFieldInfo.getColumn();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDefaultNotNull() {
        return defaultNotNull;
    }

    public void setDefaultNotNull(boolean defaultNotNull) {
        this.defaultNotNull = defaultNotNull;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}

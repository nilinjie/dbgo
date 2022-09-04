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
     * 默认为空
     */
    private boolean defaultNotNull;
    /**
     * 主键类型
     */
    private IdType idType;
    /**
     * 字段信息
     */
    private TableFieldInfo tableFieldInfo;

    public EntityFieldInfo() {
    }

    public EntityFieldInfo(TableFieldInfo tableFieldInfo) {
        this.tableFieldInfo = tableFieldInfo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TableFieldInfo getTableFieldInfo() {
        return tableFieldInfo;
    }

    public void setTableFieldInfo(TableFieldInfo tableFieldInfo) {
        this.tableFieldInfo = tableFieldInfo;
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
}

package cn.com.dbgo.core.entity;

import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

/**
 * 实体类映射表信息
 *
 * @author lingee
 * @date 2022/9/2
 */
public class EntityTableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;
    /**
     * 表字段列表
     */
    private List<EntityFieldInfo> fieldInfoList;

    public EntityTableInfo() {
    }

    public EntityTableInfo(TableInfo tableInfo) {
        this.tableName = tableInfo.getTableName();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<EntityFieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void setFieldInfoList(List<EntityFieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }
}

package cn.com.dbgo.core.dialect.entity;

import cn.com.dbgo.core.dialect.IDbTable;

import java.io.Serializable;
import java.util.Date;

/**
 * mysql数据库表信息
 *
 * @author lingee
 * @date 2022/8/31
 */
public class MySqlTable implements IDbTable, Serializable {

    private static final long serialVersionUID = 6283312998104401306L;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 表数据总行数
     */
    private Long tableRows;
    /**
     * 平均每行表数据大小
     */
    private Long avgRowLength;
    /**
     * 表数据大小
     */
    private Long dataLength;
    /**
     * 表数据最大大小
     */
    private Long maxDataLength;
    /**
     * 表字符集
     */
    private String tableCharset;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

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
    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Long getTableRows() {
        return tableRows;
    }

    public void setTableRows(Long tableRows) {
        this.tableRows = tableRows;
    }

    public Long getAvgRowLength() {
        return avgRowLength;
    }

    public void setAvgRowLength(Long avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public Long getDataLength() {
        return dataLength;
    }

    public void setDataLength(Long dataLength) {
        this.dataLength = dataLength;
    }

    public Long getMaxDataLength() {
        return maxDataLength;
    }

    public void setMaxDataLength(Long maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public String getTableCharset() {
        return tableCharset;
    }

    public void setTableCharset(String tableCharset) {
        this.tableCharset = tableCharset;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

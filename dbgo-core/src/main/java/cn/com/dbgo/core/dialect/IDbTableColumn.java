package cn.com.dbgo.core.dialect;

/**
 * 数据库表字段
 *
 * @author lingee
 * @date 2022/8/31
 */
public interface IDbTableColumn {

    /**
     * 字段名
     *
     * @return
     */
    String getColumnName();

    /**
     * 字段注释
     *
     * @return
     */
    String getColumnComment();

    /**
     * 表名
     *
     * @return
     */
    String getTableName();

    /**
     * 数据库名
     *
     * @return
     */
    String getDbName();

    /**
     * 字段类型
     *
     * @return
     */
    String getColumnType();

    /**
     * 是否主键
     *
     * @return
     */
    boolean isPrimaryKey();

    /**
     * 是否默认不为空
     *
     * @return
     */
    boolean isDefaultNotNull();

    /**
     * 是否自增字段
     *
     * @return
     */
    boolean isAutoIncrement();
}

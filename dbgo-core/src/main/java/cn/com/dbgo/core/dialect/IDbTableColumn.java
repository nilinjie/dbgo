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
     * 字段键类型
     *
     * @return
     */
    String getColumnKey();
}

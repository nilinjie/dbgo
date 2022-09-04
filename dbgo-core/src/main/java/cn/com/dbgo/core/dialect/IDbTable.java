package cn.com.dbgo.core.dialect;

/**
 * 数据库表信息
 *
 * @author lingee
 * @date 2022/8/31
 */
public interface IDbTable {

    /**
     * 表名
     *
     * @return
     */
    String getTableName();

    /**
     * 数据库名称
     *
     * @return
     */
    String getDbName();

    /**
     * 表注释
     *
     * @return
     */
    String getTableComment();
}

package cn.com.dbgo.core.service;

import cn.com.dbgo.core.dialect.IDbTable;
import cn.com.dbgo.core.dialect.IDbTableColumn;

import java.util.List;

/**
 * 数据库表服务层
 *
 * @author lingee
 * @date 2022/8/31
 */
public interface TableService {

    /**
     * 获取数据库表信息
     *
     * @return List<DbTable>
     */
    List<IDbTable> getDbTables();

    /**
     * 根据表名获取表字段信息
     *
     * @param tableName 表名
     * @return List<DbTableColumn>
     */
    List<IDbTableColumn> getDbTableColumns(String tableName);

    /**
     * 执行ddl语句
     *
     * @param sql sql语句
     */
    void executeDDL(String sql);
}

package cn.com.dbgo.core.mapper;

import cn.com.dbgo.core.dialect.IDbTable;
import cn.com.dbgo.core.dialect.IDbTableColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据表表结构操作
 *
 * @author lingee
 * @date 2022/8/19
 */
@Mapper
public interface TableMapper {

    /**
     * 获取数据库中所有表
     *
     * @return List<DbTable>
     */
    List<IDbTable> listMySqlTables();

    /**
     * 获取表所有字段
     *
     * @param tableName 表名
     * @return List<DbTableColumn>
     */
    List<IDbTableColumn> listTableColumn(String tableName);

    /**
     * 执行ddl语句
     *
     * @param sql sql语句
     */
    void executeDDL(String sql);
}

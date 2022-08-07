package cn.com.dbgo.core.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库执行接口
 *
 * @author lingee
 * @date 2022/8/7
 */
public interface DbExecutor {

    /**
     * 执行ddl语句
     *
     * @param sql ddl语句
     * @return 是否成功
     */
    boolean executeDDL(String sql) throws SQLException;

    /**
     * 新增数据
     *
     * @param sql  新增语句
     * @param args 新增参数
     * @return 自增id
     */
    int insert(String sql, Object[] args) throws SQLException;

    /**
     * 修改或删除
     *
     * @param sql  修改或删除语句
     * @param args 参数
     * @return 生效行数
     */
    int updateOrDelete(String sql, Object[] args) throws SQLException;

    /**
     * 查询列表
     *
     * @param sql  查询语句
     * @param args 查询参数
     * @return
     */
    List<Map<String, Object>> queryForList(String sql, Object[] args) throws SQLException;

    /**
     * 查询单个对象
     *
     * @param sql  查询语句
     * @param args 查询参数
     * @return
     */
    Map<String, Object> query(String sql, Object[] args) throws SQLException;
}

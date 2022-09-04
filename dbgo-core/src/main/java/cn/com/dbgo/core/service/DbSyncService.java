package cn.com.dbgo.core.service;

/**
 * sql同步服务
 *
 * @author lingee
 * @date 2022/8/20
 */
public interface DbSyncService {

    /**
     * 同步所有表结构
     */
    void syncTables();
}

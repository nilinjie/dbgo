package cn.com.dbgo.core.service.impl;

import cn.com.dbgo.core.dialect.IDbTable;
import cn.com.dbgo.core.dialect.IDbTableColumn;
import cn.com.dbgo.core.entity.EntityTableInfo;
import cn.com.dbgo.core.handler.TableHandler;
import cn.com.dbgo.core.service.DbSyncService;
import cn.com.dbgo.core.service.TableService;
import cn.com.dbgo.core.sql.SqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * sql同步服务实现
 *
 * @author lingee
 * @date 2022/8/20
 */
@Service
public class DbSyncServiceImpl implements DbSyncService {

    private static final Logger logger = LoggerFactory.getLogger(DbSyncServiceImpl.class);

    @Autowired
    private TableHandler tableHandler;

    @Autowired
    private TableService tableService;

    @Override
    public void syncTables() {
        List<EntityTableInfo> entityTableInfos = tableHandler.getTableInfos();

        if (CollectionUtils.isEmpty(entityTableInfos)) {
            return;
        }

        List<IDbTable> dbTables = tableService.getDbTables();

        if (CollectionUtils.isEmpty(dbTables)) {
            entityTableInfos.stream().forEach(entityTableInfo -> {
                String createTableSql = SqlBuilder.create()
                        .createTable(entityTableInfo.getTableName(), entityTableInfo.getComment(), entityTableInfo.getFieldInfoList())
                        .build();
                tableService.executeDDL(createTableSql);
            });
        }

        List<IDbTableColumn> IDbTableColumns = tableService.getDbTableColumns(dbTables.get(0).getTableName());

    }
}

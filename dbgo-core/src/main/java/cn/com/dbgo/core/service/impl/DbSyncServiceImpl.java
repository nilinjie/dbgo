package cn.com.dbgo.core.service.impl;

import cn.com.dbgo.core.dialect.IDbTable;
import cn.com.dbgo.core.dialect.IDbTableColumn;
import cn.com.dbgo.core.entity.EntityFieldInfo;
import cn.com.dbgo.core.entity.EntityTableInfo;
import cn.com.dbgo.core.handler.TableHandler;
import cn.com.dbgo.core.service.DbSyncService;
import cn.com.dbgo.core.service.TableService;
import cn.com.dbgo.core.sql.SqlBuilder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            return;
        }

        List<String> dbTableNames = dbTables.stream().map(IDbTable::getTableName).collect(Collectors.toList());

        for (EntityTableInfo entityTableInfo : entityTableInfos) {
            if (dbTableNames.contains(entityTableInfo.getTableName())) {
                String tableName = entityTableInfo.getTableName();
                List<IDbTableColumn> dbTableColumns = tableService.getDbTableColumns(tableName);
                String updateSql = compareColumnAndBuildSql(tableName, dbTableColumns, entityTableInfo.getFieldInfoList());
                logger.info("更新表【{}】sql语句：{}", tableName, updateSql);
                tableService.executeDDL(updateSql);
            }
        }


    }

    /**
     * 比较字段变更并构建语句
     *
     * @param tableName        表名
     * @param dbTableColumns   数据库表字段名
     * @param entityFieldInfos 实体字段列表
     * @return
     */
    private String compareColumnAndBuildSql(String tableName, List<IDbTableColumn> dbTableColumns, List<EntityFieldInfo> entityFieldInfos) {
        Map<String, IDbTableColumn> dbTableColumnMap = dbTableColumns.stream().collect(Collectors.toMap(IDbTableColumn::getColumnName, x -> x));

        SqlBuilder sqlBuilder = SqlBuilder.create();
        for (EntityFieldInfo entityFieldInfo : entityFieldInfos) {
            IDbTableColumn dbTableColumn = dbTableColumnMap.get(entityFieldInfo.getColumnName());
            if (ObjectUtils.isEmpty(dbTableColumn)) {
                sqlBuilder.addColumn(tableName, entityFieldInfo);
            } else {
                boolean isChanged = false;
                if (entityFieldInfo.isPrimaryKey() != dbTableColumn.isPrimaryKey()
                        || entityFieldInfo.isDefaultNotNull() != dbTableColumn.isDefaultNotNull()
                        || !entityFieldInfo.getColumnType().equals(dbTableColumn.getColumnType())) {
                    isChanged = true;
                }

                if (entityFieldInfo.isPrimaryKey()) {
                    if (entityFieldInfo.getIdType().equals(IdType.AUTO) != dbTableColumn.isAutoIncrement()) {
                        isChanged = true;
                    }
                }

                if (StringUtils.isNotBlank(entityFieldInfo.getComment()) != StringUtils.isNotBlank(dbTableColumn.getColumnComment())) {
                    isChanged = true;
                }

                if (StringUtils.isNotBlank(entityFieldInfo.getComment())) {
                    if (!entityFieldInfo.getComment().equals(dbTableColumn.getColumnComment())) {
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    sqlBuilder.changeColumn(tableName, entityFieldInfo);
                }
            }
        }
        return sqlBuilder.build();
    }
}

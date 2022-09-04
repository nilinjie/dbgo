package cn.com.dbgo.core.service.impl;

import cn.com.dbgo.core.dialect.IDbTable;
import cn.com.dbgo.core.dialect.IDbTableColumn;
import cn.com.dbgo.core.mapper.TableMapper;
import cn.com.dbgo.core.service.TableService;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 数据库表服务实现层
 *
 * @author lingee
 * @date 2022/8/31
 */
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    private DbType dbType;

    @PostConstruct
    public void init() {
        dbType = JdbcUtils.getDbType(dataSourceProperties.getUrl());
    }

    @Override
    public List<IDbTable> getDbTables() {
        switch (dbType) {
            case MYSQL:
                return tableMapper.listMySqlTables();
            case ORACLE:
                //TODO: 各种类型数据库
            default:
        }
        return null;
    }

    @Override
    public List<IDbTableColumn> getDbTableColumns(String tableName) {
        switch (dbType) {
            case MYSQL:
                return tableMapper.listTableColumn(tableName);
            case ORACLE:
                //TODO: 各种类型数据库
            default:
        }
        return null;
    }
}

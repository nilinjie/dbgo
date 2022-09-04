package cn.com.dbgo.core.sql;


import cn.com.dbgo.core.entity.EntityFieldInfo;
import cn.com.dbgo.core.support.Builder;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlCharExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlAlterTableChangeColumn;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;

import java.util.List;

/**
 * sql处理工具类
 *
 * @author lingee
 * @date 2022/4/22
 */
public class SqlBuilder implements Builder<String> {

    /**
     * 创建SQL构建器
     *
     * @return SQL构建器
     */
    public static SqlBuilder create() {
        return new SqlBuilder();
    }


    /**
     * 创建SQL构建器
     *
     * @param dbType 数据库类型
     * @return SQL构建器
     */
    public static SqlBuilder create(DbType dbType) {
        return new SqlBuilder(dbType);
    }

    /**
     * 创建SQL构建器
     *
     * @param dbType  数据库类型
     * @param wrapper 包装器
     * @return
     */
    public static SqlBuilder create(DbType dbType, Wrapper wrapper) {
        return new SqlBuilder(dbType, wrapper);
    }

    /**
     * sql构造容器
     */
    private final StringBuilder sql = new StringBuilder();

    /**
     * 数据库类型
     */
    private DbType dbType = DbType.mysql;

    /**
     * 包装器
     */
    private Wrapper wrapper = new Wrapper('`');

    public SqlBuilder() {
    }

    public SqlBuilder(DbType dbType) {
        this.dbType = dbType;
    }

    public SqlBuilder(DbType dbType, Wrapper wrapper) {
        this.dbType = dbType;
        this.wrapper = wrapper;
    }

    /**
     * 组装创建表结构语句
     *
     * @param tableName    表名
     * @param tableComment 表注释
     * @param entityFields 实体字段列表
     * @return SqlBuilder
     */
    public SqlBuilder createTable(String tableName,
                                  String tableComment,
                                  List<EntityFieldInfo> entityFields) {
        SQLCreateTableStatement createTableStatement;
        switch (dbType) {
            case mysql:
                createTableStatement = new MySqlCreateTableStatement();
                createTableStatement.setEngine(new MySqlCharExpr("InnoDB"));
                createTableStatement.addOption("DEFAULT CHARSET", new MySqlCharExpr("utf8mb4"));
                break;
            default:
                createTableStatement = new SQLCreateTableStatement();
        }

        for (EntityFieldInfo entityFieldInfo : entityFields) {
            createTableStatement.addColumn(buildSQLColumnDefinition(entityFieldInfo));
        }

        createTableStatement.setName(tableName);
        createTableStatement.setComment(new SQLCharExpr(tableComment));
        sql.append(SQLUtils.toSQLString(createTableStatement, dbType));
        return this;
    }

    /**
     * 构建修改表字段语句（仅mysql使用）
     *
     * @param tableName    表名
     * @param entityFields 实体字段列表
     * @return SqlBuilder
     */
    public SqlBuilder changeColumns(String tableName,
                                    List<EntityFieldInfo> entityFields) {
        SQLAlterTableStatement alterTableStatement = new SQLAlterTableStatement();
        alterTableStatement.setTableSource(new SQLExprTableSource(tableName));

        for (EntityFieldInfo entityFieldInfo : entityFields) {
            MySqlAlterTableChangeColumn alterTableChangeColumn = new MySqlAlterTableChangeColumn();
            alterTableChangeColumn.setColumnName(new SQLIdentifierExpr(wrapper.wrap(entityFieldInfo.getTableFieldInfo().getColumn())));
            alterTableChangeColumn.setNewColumnDefinition(buildSQLColumnDefinition(entityFieldInfo));

            alterTableStatement.addItem(alterTableChangeColumn);
        }

        sql.append(SQLUtils.toSQLString(alterTableStatement, dbType));
        return this;
    }

    /**
     * 组装表增加字段语句
     *
     * @param tableName    表名
     * @param entityFields 实体字段列表
     * @return SqlBuilder
     */
    public SqlBuilder addColumns(String tableName,
                                 List<EntityFieldInfo> entityFields) {

        SQLAlterTableStatement alterTableStatement = new SQLAlterTableStatement();
        alterTableStatement.setTableSource(new SQLExprTableSource(tableName));

        for (EntityFieldInfo entityFieldInfo : entityFields) {
            SQLAlterTableAddColumn alterTableAddColumn = new SQLAlterTableAddColumn();
            alterTableAddColumn.addColumn(buildSQLColumnDefinition(entityFieldInfo));

            alterTableStatement.addItem(alterTableAddColumn);
        }

        sql.append(SQLUtils.toSQLString(alterTableStatement, dbType));
        return this;
    }

    /**
     * 组装删除表字段语句
     *
     * @param tableName    表名
     * @param entityFields 实体字段列表
     * @return SqlBuilder
     */
    public SqlBuilder dropColumns(String tableName,
                                  List<EntityFieldInfo> entityFields) {
        SQLAlterTableStatement alterTableStatement = new SQLAlterTableStatement();
        alterTableStatement.setTableSource(new SQLExprTableSource(tableName));

        for (EntityFieldInfo entityFieldInfo : entityFields) {
            SQLAlterTableDropColumnItem dropColumnItem = new SQLAlterTableDropColumnItem();
            dropColumnItem.addColumn((new SQLIdentifierExpr(wrapper.wrap(entityFieldInfo.getTableFieldInfo().getColumn()))));

            alterTableStatement.addItem(dropColumnItem);
        }

        sql.append(SQLUtils.toSQLString(alterTableStatement, dbType));
        return this;

    }

    /**
     * 删除表结构
     *
     * @param tableName 表名
     * @return SqlBuilder
     */
    public SqlBuilder dropTable(String tableName) {
        SQLDropTableStatement statement = new SQLDropTableStatement();
        statement.addTableSource(new SQLExprTableSource(tableName));
        sql.append(SQLUtils.toSQLString(statement, dbType));
        return this;
    }


    @Override
    public String build() {
        return this.sql.toString();
    }

    @Override
    public String toString() {
        return this.build();
    }


    /**
     * 构建数据列定义信息
     *
     * @param fieldInfo 字段实体映射信息
     * @return
     */
    private SQLColumnDefinition buildSQLColumnDefinition(EntityFieldInfo fieldInfo) {
        TableFieldInfo tableFieldInfo = fieldInfo.getTableFieldInfo();

        SQLColumnDefinition sqlColumnDefinition = new SQLColumnDefinition();

        sqlColumnDefinition.setName(wrapper.wrap(tableFieldInfo.getColumn()));
        sqlColumnDefinition.setComment(fieldInfo.getComment());
        sqlColumnDefinition.setDataType(new SQLCharacterDataType(tableFieldInfo.getTypeHandler().getTypeName()));
        sqlColumnDefinition.setAutoIncrement(IdType.AUTO.equals(fieldInfo.getIdType()));

        if (tableFieldInfo.isPrimitive()) {
            sqlColumnDefinition.addConstraint(new SQLColumnPrimaryKey());
        }
        if (fieldInfo.isDefaultNotNull()) {
            sqlColumnDefinition.addConstraint(new SQLNotNullConstraint());
        } else {
            sqlColumnDefinition.addConstraint(new SQLNullConstraint());
        }
        return sqlColumnDefinition;
    }
}

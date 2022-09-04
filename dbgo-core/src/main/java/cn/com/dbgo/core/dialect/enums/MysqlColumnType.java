package cn.com.dbgo.core.dialect.enums;

import cn.com.dbgo.core.dialect.IDbColumnType;
import cn.com.dbgo.core.support.JavaDataType;

/**
 * mysql数据库字段类型
 *
 * @author lingee
 * @date 2022/9/4
 */
public enum MysqlColumnType implements IDbColumnType {

    VARCHAR("varchar"),
    CHAR("char"),
    TEXT("text"),
    JSON("json"),
    ENUM("enum"),
    BIGINT("bigint"),
    TINYINT("tinyint"),
    BIT("bit"),
    INT("int"),
    DECIMAL("decimal"),
    CLOB("clob"),
    BLOB("blob"),
    BINARY("binary"),
    FLOAT("float"),
    DOUBLE("double"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
    TIMESTAMP("timestamp");

    private final String type;

    MysqlColumnType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}

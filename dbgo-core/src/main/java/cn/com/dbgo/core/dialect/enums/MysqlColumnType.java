package cn.com.dbgo.core.dialect.enums;

/**
 * mysql数据库字段类型
 *
 * @author lingee
 * @date 2022/9/4
 */
public enum MysqlColumnType {

    VARCHAR("varchar(255)"),
    CHAR("char(20)"),
    TEXT("text"),
    JSON("json"),
    ENUM("enum(2)"),
    BIGINT("bigint(14)"),
    TINYINT("tinyint(1)"),
    BIT("bit(1)"),
    INT("int(11)"),
    DECIMAL("decimal(6,2)"),
    CLOB("clob"),
    BLOB("blob"),
    BINARY("binary"),
    FLOAT("float(6,2)"),
    DOUBLE("double(6,2)"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
    TIMESTAMP("timestamp");

    private final String type;

    MysqlColumnType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
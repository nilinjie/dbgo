package cn.com.dbgo.core.support;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import sun.misc.FDBigInteger;

import java.lang.reflect.Field;

/**
 * java数据类型
 *
 * @author lingee
 * @date 2022/9/4
 */
public enum JavaDataType {

    // 基本类型
    BASE_BYTE("byte", null),
    BASE_SHORT("short", null),
    BASE_CHAR("char", null),
    BASE_INT("int", null),
    BASE_LONG("long", null),
    BASE_FLOAT("float", null),
    BASE_DOUBLE("double", null),
    BASE_BOOLEAN("boolean", null),

    // 包装类型
    BYTE("Byte", "java.lang.Byte"),
    SHORT("Short", "java.lang.Short"),
    CHARACTER("Character", "java.lang.Character"),
    INTEGER("Integer", "java.lang.Integer"),
    LONG("Long", "java.lang.Long"),
    FLOAT("Float", "java.lang.Float"),
    DOUBLE("Double", "java.lang.Double"),
    BOOLEAN("Boolean", "java.lang.Boolean"),
    STRING("String", "java.lang.String"),

    // sql 包下数据类型
    DATE_SQL("Date", "java.sql.Date"),
    TIME("Time", "java.sql.Time"),
    TIMESTAMP("Timestamp", "java.sql.Timestamp"),
    BLOB("Blob", "java.sql.Blob"),
    CLOB("Clob", "java.sql.Clob"),

    // java8 新时间类型
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    YEAR("Year", "java.time.Year"),
    YEAR_MONTH("YearMonth", "java.time.YearMonth"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
    INSTANT("Instant", "java.time.Instant"),

    // 其他杂类
    MAP("Map", "java.util.Map"),
    BYTE_ARRAY("byte[]", null),
    OBJECT("Object", null),
    DATE("Date", "java.util.Date"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");

    private final String type;

    private final String pkg;

    JavaDataType(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    public static JavaDataType resolveJavaDataType(Field field) {
        String dataType = field.getGenericType().toString();
        for (JavaDataType value : JavaDataType.values()) {
            if (value.getType().equals(dataType)) {
                return value;
            }
            if (StringUtils.isNotBlank(value.getPkg()) && dataType.contains(value.getPkg())) {
                return value;
            }
        }
        return JavaDataType.STRING;
    }

    public String getType() {
        return type;
    }

    public String getPkg() {
        return pkg;
    }
}

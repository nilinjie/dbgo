package cn.com.dbgo.core.dialect.convert;

import cn.com.dbgo.core.dialect.ITypeConvert;
import cn.com.dbgo.core.dialect.enums.MysqlColumnType;
import cn.com.dbgo.core.support.JavaDataType;

import java.lang.reflect.Field;

/**
 * mysql类型转换
 *
 * @author lingee
 * @date 2022/9/3
 */
public class MysqlTypeConvert implements ITypeConvert {

    @Override
    public String convertToDbColumnType(Field field) {
        JavaDataType javaDataType = JavaDataType.resolveJavaDataType(field);
        switch (javaDataType) {
            case SHORT:
            case BASE_INT:
            case INTEGER:
                return MysqlColumnType.INT.getType();
            case BIG_INTEGER:
            case BASE_LONG:
            case LONG:
                return MysqlColumnType.BIGINT.getType();
            case BASE_FLOAT:
            case FLOAT:
            case BASE_DOUBLE:
            case DOUBLE:
            case BIG_DECIMAL:
                return MysqlColumnType.DECIMAL.getType();
            case BASE_BOOLEAN:
            case BOOLEAN:
                return MysqlColumnType.BIT.getType();
            case DATE:
            case DATE_SQL:
            case LOCAL_DATE:
            case LOCAL_DATE_TIME:
                return MysqlColumnType.DATETIME.getType();
            default:
                return MysqlColumnType.VARCHAR.getType();
        }
    }
}

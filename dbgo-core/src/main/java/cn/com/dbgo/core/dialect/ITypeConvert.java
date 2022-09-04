package cn.com.dbgo.core.dialect;

import java.lang.reflect.Field;

/**
 * 类型转换接口
 *
 * @author lingee
 * @date 2022/9/3
 */
public interface ITypeConvert {

    /**
     * 将java数据类型转换为数据库字段类型
     *
     * @param field 字段信息
     * @return
     */
    String convertToDbColumnType(Field field);
}

package cn.com.dbgo.core.annotation;

import java.lang.annotation.*;

/**
 * 字段描述
 *
 * @author lingee
 * @date 2022/9/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldComment {

    /**
     * 字段描述
     *
     * @return
     */
    String value() default "";

    /**
     * 字段数据类型
     * eg: varchar(100),decimal(8,2)
     *
     * @return
     */
    String columnType() default "";

    /**
     * 是否必填
     *
     * @return
     */
    boolean required() default false;
}

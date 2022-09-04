package cn.com.dbgo.core.annotation;

import java.lang.annotation.*;

/**
 * 表描述
 *
 * @author lingee
 * @date 2022/9/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableComment {

    String value() default "";
}

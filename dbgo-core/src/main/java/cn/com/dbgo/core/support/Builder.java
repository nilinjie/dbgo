package cn.com.dbgo.core.support;

import java.io.Serializable;

/**
 * 建造者模式接口定义
 *
 * @author lingee
 * @date 2022/5/7
 */
public interface Builder<T> extends Serializable {

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();
}

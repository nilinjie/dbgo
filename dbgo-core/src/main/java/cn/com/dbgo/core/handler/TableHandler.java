package cn.com.dbgo.core.handler;

import cn.com.dbgo.core.annotation.FieldComment;
import cn.com.dbgo.core.annotation.TableComment;
import cn.com.dbgo.core.entity.EntityFieldInfo;
import cn.com.dbgo.core.entity.EntityTableInfo;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表格处理器
 *
 * @author lingee
 * @date 2022/8/18
 */
@Component
public class TableHandler {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取表映射实体信息
     *
     * @return
     */
    public List<EntityTableInfo> getTableInfos() {
        //根据接口类型返回相应的所有bean
        Map<String, BaseMapper> map = applicationContext.getBeansOfType(BaseMapper.class);

        List<EntityTableInfo> tableInfoList = new ArrayList<>();

        for (Map.Entry<String, BaseMapper> entry : map.entrySet()) {
            Type actualType = getTarget(entry.getValue())[0];

            Class<?> clazz = (Class<?>) actualType;

            TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), clazz);

            if (ObjectUtils.isNotEmpty(tableInfo)) {

                EntityTableInfo entityTableInfo = new EntityTableInfo(tableInfo);

                TableComment comment = clazz.getAnnotation(TableComment.class);
                if (ObjectUtils.isNotEmpty(comment)) {
                    entityTableInfo.setComment(comment.value());
                }

                List<TableFieldInfo> fieldInfoList = tableInfo.getFieldList();
                if (CollectionUtils.isNotEmpty(fieldInfoList)) {
                    List<EntityFieldInfo> entityFieldInfoList = new ArrayList<>();
                    fieldInfoList.stream().forEach(field -> {
                        EntityFieldInfo entityFieldInfo = new EntityFieldInfo(field);
                        FieldComment fieldComment = field.getField().getAnnotation(FieldComment.class);
                        if (ObjectUtils.isNotEmpty(fieldComment)) {
                            entityFieldInfo.setComment(fieldComment.value());
                            entityFieldInfo.setDefaultNotNull(fieldComment.required());
                        }
                        entityFieldInfo.setIdType(tableInfo.getIdType());
                        entityFieldInfoList.add(entityFieldInfo);
                    });
                    entityTableInfo.setFieldInfoList(entityFieldInfoList);
                }

                tableInfoList.add(entityTableInfo);
            }
        }

        return tableInfoList;
    }

    /**
     * 获取代理类目标对象
     *
     * @param proxy 代理对象
     * @return
     */
    private Type[] getTarget(Object proxy) {
        //判断是否是代理对象
        if (!(Proxy.getInvocationHandler(proxy) instanceof MybatisMapperProxy)) {
            return null;
        }
        if (!Proxy.isProxyClass(proxy.getClass()) || !(Proxy.getInvocationHandler(proxy) instanceof MybatisMapperProxy)) {
            throw new IllegalArgumentException("Input must be Mapper proxy instance!");
        }
        //第一个getGenericInterfaces()获取自己写的Mapper接口
        //第二个getGenericInterfaces()获取自己写的Mapper接口继承的接口
        //getActualTypeArguments()获取继承的接口的泛型信息
        return ((ParameterizedType) ((Class<?>) proxy.getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0]).getActualTypeArguments();
    }

}

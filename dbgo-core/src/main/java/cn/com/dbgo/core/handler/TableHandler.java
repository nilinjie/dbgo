package cn.com.dbgo.core.handler;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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

    public List<TableInfo> getTableInfos() {
        //根据接口类型返回相应的所有bean
        Map<String, BaseMapper> map = applicationContext.getBeansOfType(BaseMapper.class);

        List<TableInfo> tableInfoList = new ArrayList<>();

        for (Map.Entry<String, BaseMapper> entry : map.entrySet()) {
            Type[] actualTypes =getTarget(entry.getValue());
            TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), (Class<?>) actualTypes[0]);
            tableInfoList.add(tableInfo);
        }


        return tableInfoList;
    }

    public Type[] getTarget(Object proxy) {
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

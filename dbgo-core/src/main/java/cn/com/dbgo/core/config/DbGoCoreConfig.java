package cn.com.dbgo.core.config;

import cn.com.dbgo.core.dialect.ITypeConvert;
import cn.com.dbgo.core.dialect.convert.MysqlTypeConvert;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DbGo配置类
 *
 * @author lingee
 * @date 2022/9/4
 */
@Configuration
public class DbGoCoreConfig {

    @Bean
    public ITypeConvert getTypeConvert(DataSourceProperties dataSourceProperties) {
        DbType dbType = JdbcUtils.getDbType(dataSourceProperties.getUrl());
        switch (dbType) {
            case MYSQL:
            default:
                return new MysqlTypeConvert();

        }
    }
}

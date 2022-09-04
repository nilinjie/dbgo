package cn.com.dbgo.core.config;


import cn.com.dbgo.core.entity.EntityTableInfo;
import cn.com.dbgo.core.entity.User;
import cn.com.dbgo.core.handler.TableHandler;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lingee
 * @date 2022/8/7
 */
@DisplayName("数据库执行单元测试")
@SpringBootTest
class DbExecutorTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TableHandler tableHandler;

    @Test
    @DisplayName("执行ddl语句")
    void executeDDL() {
        jdbcTemplate.execute("CREATE TABLE `sys_role_01` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
                "  `role_code` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色编码',\n" +
                "  `role_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',\n" +
                "  `role_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色描述',\n" +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `uk_role_code` (`role_code`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统角色表';");
    }

    @Test
    void testGetTableInfo() {
        List<EntityTableInfo> entityTableInfos = tableHandler.getTableInfos();
        System.out.println(entityTableInfos.toArray());
    }
}
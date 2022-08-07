package cn.com.dbgo.core.db;


import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * @author lingee
 * @date 2022/8/7
 */
class DbExecutorTest {

    @Test
    void executeDDL() {
        DbExecutor executor = new DefaultDbExecutor();
        try {
            executor.executeDDL("CREATE TABLE `sys_role_01` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
                    "  `role_code` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色编码',\n" +
                    "  `role_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',\n" +
                    "  `role_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色描述',\n" +
                    "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE KEY `uk_role_code` (`role_code`) USING BTREE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统角色表';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
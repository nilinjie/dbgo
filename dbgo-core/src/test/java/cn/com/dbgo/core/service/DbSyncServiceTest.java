package cn.com.dbgo.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 数据库同步单元测试
 *
 * @author lingee
 * @date 2022/8/20
 */
@DisplayName("数据库同步单元测试")
@SpringBootTest
public class DbSyncServiceTest {

    @Autowired
    private DbSyncService dbSyncService;

    @Test
    void testSyncTables() {
        dbSyncService.syncTables();
    }
}

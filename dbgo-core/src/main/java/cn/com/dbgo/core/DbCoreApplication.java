package cn.com.dbgo.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 核心模块启动类
 *
 * @author lingee
 * @date 2022/8/14
 */
@SpringBootApplication(scanBasePackages = {"cn.com.dbgo.core"})
@EnableTransactionManagement
public class DbCoreApplication {

}

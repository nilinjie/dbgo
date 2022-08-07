package cn.com.dbgo.core.db.support;

/**
 * 数据源类型
 *
 * @author lingee
 * @date 2022/8/7
 */
public enum DbType {

    mysql("com.mysql.cj.jdbc.Driver"),

    oracle("oracle.jdbc.driver.OracleDriver"),

    dm("dm.jdbc.driver.DmDriver");

    private String driverName;

    DbType(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }
}

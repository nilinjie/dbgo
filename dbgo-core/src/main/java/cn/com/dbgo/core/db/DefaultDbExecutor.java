package cn.com.dbgo.core.db;

import cn.com.dbgo.core.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库访问器
 *
 * @author lingee
 * @date 2022/8/7
 */
public class DefaultDbExecutor implements DbExecutor {

    /**
     * 连接地址
     */
    private static String url;
    /**
     * 用户名
     */
    private static String username;
    /**
     * 密码
     */
    private static String password;
    /**
     * 驱动名称
     */
    private static String driverName;
    /**
     * 连接对象
     */
    private volatile Connection connection = null;

    static {
        PropertiesUtil propertiesUtil = PropertiesUtil.getPropertiesUtil();

        String driverClassName = (String) propertiesUtil.readValue("spring.datasource.driver-class-name");
        if (driverClassName == null || "".equals(driverClassName.trim())) {
            // 默认加载jdbc驱动
            driverName = "com.mysql.cj.jdbc.Driver";
        } else {
            driverName = driverClassName;
        }

        url = (String) propertiesUtil.readValue("spring.datasource.url");
        username = (String) propertiesUtil.readValue("spring.datasource.username");
        password = (String) propertiesUtil.readValue("spring.datasource.password");
    }

    @Override
    public boolean executeDDL(String sql) throws SQLException {
        openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.execute();
    }

    @Override
    public int insert(String sql, Object[] args) throws SQLException {
        openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = -1;
        try {
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int j = 0; j < args.length; j++) {
                pstmt.setObject(j + 1, args[j]);
            }
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    @Override
    public int updateOrDelete(String sql, Object[] args) throws SQLException {
        openConnection();
        PreparedStatement pstmt = null;
        int i = 0;
        try {
            openConnection();
            pstmt = connection.prepareStatement(sql);
            for (int j = 0; j < args.length; j++) {
                pstmt.setObject(j + 1, args[j]);
            }
            // 执行sql，返回改变的行数
            i = pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return i;
    }

    public List<Map<String, Object>> queryForList(String sql, Object[] args) throws SQLException {
        openConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            openConnection();
            pstmt = connection.prepareStatement(sql);
            for (int j = 0; j < args.length; j++) {
                pstmt.setObject(j + 1, args[j]);
            }
            // 执行sql，返回查询到的set集合
            rs = pstmt.executeQuery();
            if (rs != null) {
                resultList = toListMap(rs);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }

    public Map<String, Object> query(String sql, Object[] args) throws SQLException {
        openConnection();
        List<Map<String, Object>> list = queryForList(sql, args);
        if (list != null && list.size() > 0) {
            if (list.size() > 1) {
                throw new SQLException("The number of results exceeds 1.");
            }
            return list.get(0);
        }
        return null;
    }


    /**
     * 开启连接
     */
    protected void openConnection() throws SQLException {
        if (connection == null) {
            synchronized (this) {
                if (connection == null) {
                    try {
                        Class.forName(driverName);
                        connection = DriverManager.getConnection(url, username, password);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 关闭连接
     */
    protected void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * ResultSet转List<Map<String, Object>>
     *
     * @param ret 结果集
     * @return List<Map < String, Object>>
     * @throws SQLException
     */
    protected List<Map<String, Object>> toListMap(ResultSet ret) throws SQLException {
        List<Map<String, Object>> list = new ArrayList();
        ResultSetMetaData meta = ret.getMetaData();
        int cot = meta.getColumnCount();

        while (ret.next()) {
            Map<String, Object> map = new HashMap();
            for (int i = 0; i < cot; i++) {
                map.put(meta.getColumnName(i + 1), ret.getObject(i + 1));
            }
            list.add(map);
        }

        return list;
    }
}

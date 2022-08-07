package cn.com.dbgo.core.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.*;

/**
 * 配置文件工具
 *
 * @author lingee
 * @date 2022/8/6
 */
public class PropertiesUtil {

    /**
     * properties配置文件列表
     */
    private final List<Properties> props = new ArrayList<>();
    /**
     * yml文件列表
     */
    private final List<Map> yamls = new ArrayList<>();

    private static volatile PropertiesUtil propertiesUtil;

    private PropertiesUtil() {
    }

    public static PropertiesUtil getPropertiesUtil() {
        if (propertiesUtil == null) {
            synchronized (PropertiesUtil.class) {
                if (propertiesUtil == null) {
                    propertiesUtil = new PropertiesUtil();
                    try {
                        URI uri = PropertiesUtil.class.getClassLoader().getResource("").toURI();
                        File[] files = new File(uri).listFiles();

                        for (File file : files) {
                            if (file.isDirectory()) {
                                continue;
                            }
                            String key = file.getName();
                            InputStream propIn = PropertiesUtil.class.getClassLoader().getResourceAsStream(key);
                            if (key.endsWith(".properties")) {
                                Properties prop = new Properties();
                                prop.load(propIn);
                                propertiesUtil.props.add(prop);
                            } else if (key.endsWith(".yml")) {
                                Reader readerYml = new InputStreamReader(propIn);
                                Yaml yaml = new Yaml();
                                propertiesUtil.yamls.add(yaml.loadAs(readerYml, Map.class));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return propertiesUtil;
                }
            }
        }
        return propertiesUtil;
    }

    /**
     * 根据key值读取配置的值
     *
     * @param key 配置属性名
     * @return Object
     */
    public Object readValue(String key) {
        if (props.size() > 0) {
            for (Properties prop : props) {
                if (prop.get(key) != null) {
                    return prop.get(key);
                }
            }
        }
        if (yamls.size() > 0) {
            for (Map yaml : yamls) {
                if (key.contains(".")) {
                    String[] keys = key.split("\\.");
                    return getValueFromMap(yaml, keys, 0);
                } else {
                    if (yaml.get(key) != null) {
                        return yaml.get(key);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 读取配置文件的全部信息
     *
     * @return Map<String, String>
     */
    public Map readAllProperties() {
        Map<Object, Object> map = new HashMap<>();
        if (props.size() > 0) {
            for (Properties prop : props) {
                for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }

        }
        if (yamls.size() > 0) {
            for (Map yaml : yamls) {
                map.putAll(yaml);
            }
        }
        return map;
    }

    /**
     * 递归获取yml配置值
     *
     * @param map
     * @param keys
     * @param i
     * @return
     */
    private Object getValueFromMap(Map map, String[] keys, int i) {
        if (map.get(keys[i]) instanceof Map) {
            return getValueFromMap((Map) map.get(keys[i]), keys, ++i);
        }
        return map.get(keys[i]);
    }

    public static void main(String[] args) {
        PropertiesUtil p = PropertiesUtil.getPropertiesUtil();
        System.out.println(p.readValue("spring.datasource.url"));
    }
}

package cn.com.dbgo.core.sql;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * 包装器<br>
 * 主要用于字段名的包装（在字段名的前后加字符，例如反引号来避免与数据库的关键字冲突）
 *
 * @author lingee
 * @date 2022/5/7
 */
public class Wrapper implements Serializable {

    private static final long serialVersionUID = -1205367593513792007L;

    /**
     * 前置包装符号
     */
    private Character preWrapQuote;
    /**
     * 后置包装符号
     */
    private Character sufWrapQuote;

    public Wrapper() {
    }

    /**
     * 构造
     *
     * @param wrapQuote 单包装字符
     */
    public Wrapper(Character wrapQuote) {
        this.preWrapQuote = wrapQuote;
        this.sufWrapQuote = wrapQuote;
    }

    /**
     * 包装符号
     *
     * @param preWrapQuote 前置包装符号
     * @param sufWrapQuote 后置包装符号
     */
    public Wrapper(Character preWrapQuote, Character sufWrapQuote) {
        this.preWrapQuote = preWrapQuote;
        this.sufWrapQuote = sufWrapQuote;
    }

    /**
     * @return 前置包装符号
     */
    public char getPreWrapQuote() {
        return preWrapQuote;
    }

    /**
     * 设置前置包装的符号
     *
     * @param preWrapQuote 前置包装符号
     */
    public void setPreWrapQuote(Character preWrapQuote) {
        this.preWrapQuote = preWrapQuote;
    }

    /**
     * @return 后置包装符号
     */
    public char getSufWrapQuote() {
        return sufWrapQuote;
    }

    /**
     * 设置后置包装的符号
     *
     * @param sufWrapQuote 后置包装符号
     */
    public void setSufWrapQuote(Character sufWrapQuote) {
        this.sufWrapQuote = sufWrapQuote;
    }

    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param field 字段名
     * @return 包装后的字段名
     */
    public String wrap(String field) {
        if (preWrapQuote == null || sufWrapQuote == null || StringUtils.isBlank(field)) {
            return field;
        }
        //如果已经包含包装的引号，返回原字符
        if (this.isSurround(field, preWrapQuote, sufWrapQuote)) {
            return field;
        }
        //如果字段中包含通配符或者括号（字段通配符或者函数），不做包装
        String[] excludedStr = {"*", "(", " ", " as "};
        for (String s : excludedStr) {
            if (field.toLowerCase().indexOf(s) >= 0) {
                return field;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        //对于字段带表名前缀的需要单独拆分包装
        if (field.contains(".")) {
            String[] targets = field.split("\\.");
            stringBuilder.append(preWrapQuote).append(targets[0]).append(sufWrapQuote)
                    .append(".")
                    .append(preWrapQuote).append(targets[1]).append(sufWrapQuote);
        } else {
            stringBuilder.append(preWrapQuote).append(field).append(sufWrapQuote);
        }

        return stringBuilder.toString();
    }

    /**
     * 给定字符串是否被字符包围
     *
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否包围，空串不包围
     */
    private boolean isSurround(CharSequence str, char prefix, char suffix) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        if (str.length() < 2) {
            return false;
        }

        return str.charAt(0) == prefix && str.charAt(str.length() - 1) == suffix;
    }


    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param fields 字段名
     * @return 包装后的字段名
     */
    public String[] wrap(String... fields) {
        if (ArrayUtils.isEmpty(fields)) {
            return fields;
        }

        String[] wrappedFields = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            wrappedFields[i] = wrap(fields[i]);
        }

        return wrappedFields;
    }

    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param fields 字段名
     * @return 包装后的字段名
     */
    public Collection<String> wrap(Collection<String> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return fields;
        }

        return Arrays.asList(wrap(fields.toArray(new String[0])));
    }

}

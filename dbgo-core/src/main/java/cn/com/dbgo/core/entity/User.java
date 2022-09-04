package cn.com.dbgo.core.entity;

import cn.com.dbgo.core.annotation.FieldComment;
import cn.com.dbgo.core.annotation.TableComment;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author lingee
 * @date 2022/8/14
 */
@TableComment(value = "用户信息")
public class User implements Serializable {

    private static final long serialVersionUID = -1750395157304846628L;

    @FieldComment(value = "主键", required = true)
    private Long id;

    @FieldComment(value = "姓名", required = true, maxLength = 255)
    private String name;

    @FieldComment(value = "年龄")
    private Integer age;

    @FieldComment(value = "邮箱", maxLength = 20)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

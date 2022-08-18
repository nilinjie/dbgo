package cn.com.dbgo.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author lingee
 * @date 2022/8/14
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1750395157304846628L;

    private Long id;

    private String name;

    private Integer age;

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

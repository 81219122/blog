package com.zhangrun.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 10:59
 * 博客分类
 */
@Entity
@Table(name="t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;  //分类名称

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>(); //一个分类下有多个博客 一对多  type与blogtype是被维护端

    public Type() {
    }

    @Override
    public String toString() {
        return "Type{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

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

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}

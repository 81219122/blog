package com.zhangrun.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 11:01
 * 标签类
 */
@Entity
@Table(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;  //标签名称

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=new ArrayList<>(); //一个标签对应多个博客  (博客类与标签类多对多) tag与blogtag是被维护端

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
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
}

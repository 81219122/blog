package com.zhangrun.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 11:03
 * 评论类
 */
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;  //昵称
    private String email;  //邮箱
    private String content; //评论内容
    private String avatar;  //头像
    @Temporal(TemporalType.TIMESTAMP)  //对应生成数据库的时间类型 这个参数生成的时间比较全
    private Date creatTime; //创建时间

    @ManyToOne
    private Blog blog; //一个评论只能对应一篇博客

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments=new ArrayList<>(); //包含多个回复的子对象 子类对象只能有一个相连父类对象

    @ManyToOne
    private Comment parentComment; //一个子类对象只能有一个相连父类对象

    private Boolean adminComment; //标记是否是博主评论

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", nickname='" + nickname + '\'' + ", email='" + email + '\'' + ", content='" + content + '\'' + ", avatar='" + avatar + '\'' + ", creatTime=" + creatTime + '}';
    }

    public Comment() {
    }

    public Boolean getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Boolean adminComment) {
        this.adminComment = adminComment;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}

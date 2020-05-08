package com.zhangrun.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 10:50
 * 博客类
 * 注解：entity 标准为实体类 table 给表重命名 id 设置表的主键 GeneratedValue 主键自动生成
 */
@Entity
@Table(name="t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title; //标题
    /*懒加载 时候的时候才去加载 Lob标注大字段
    @Basic(fetch = FetchType.LAZY)
    @Lob*/
    private String content;  //内容
    private String firstPicture;  //首图
    private String flag; //标记
    private Integer views; //浏览次数
    private boolean appreciation; //赞赏是否开启
    private boolean shareStatment; //转载声明是否开启
    private boolean commentabled; //评论是否开启
    private boolean published; //是否发布
    private boolean recommend;  //是否推荐
    @Temporal(TemporalType.TIMESTAMP)  //对应生成数据库的时间类型 这个参数生成的时间比较全 日期和时间都有
    private Date createTime; //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime; //更新时间

    @ManyToOne
    private Type type;// 一个博客对应一种分类  这是多的一端

    @ManyToMany(cascade = {CascadeType.PERSIST})  //设置级联新增 新增blog时选择一个标签
    private List<Tag> tags=new ArrayList<>(); //一个博客对应多个标签  (博客类与标签类多对多)
    @ManyToOne  // 关系的维护一端
    private User user;  //一篇博客对应一个用户

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>(); //一篇博客可以有多个评论

    @Transient
    private String tagIds;  //不用写入数据库,方便页面传参数Tag的id到blog对象

    private String description; //博客描述


    public Blog() {
    }

    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }
    private String tagsToIds(List<Tag> tags){
        if (tags.size()==0||tags==null) return tagIds;
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<tags.size();i++){
            if (i<tags.size()){
                sb.append(tags.get(i).getId()+",");
            }else if (i==tags.size()){
                sb.append(tags.get(i).getId());
            }
        }
        return sb.toString();
    }
    /*private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }*/
    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", title='" + title + '\'' + ", content='" + content + '\'' + ", firstPicture='" + firstPicture + '\'' + ", flag='" + flag + '\'' + ", views=" + views + ", appreciation=" + appreciation + ", shareStatment=" + shareStatment + ", commentabled=" + commentabled + ", published=" + published + ", recommend=" + recommend + ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatment() {
        return shareStatment;
    }

    public void setShareStatment(boolean shareStatment) {
        this.shareStatment = shareStatment;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

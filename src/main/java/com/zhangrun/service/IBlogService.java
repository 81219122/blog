package com.zhangrun.service;

import com.zhangrun.entity.Blog;
import com.zhangrun.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 10:19
 */
public interface IBlogService {
    //根据主键查询博客对象
    Blog getBlog(Long id);

    //分页查询博客对象 及模糊查询
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    //分页查询博客对象 不用条件
    Page<Blog> listBlog(Pageable pageable);

    //新增博客对象
    Blog save(Blog blog);

    //修改博客
    Blog updateBlog(Long id,Blog blog);

    //删除博客
    void deleteBlog(Long id);

    //博客首页查询的博客推荐
    List<Blog> listBlogTop(Integer size);

    //根据博客首页条件查询
    Page<Blog> listBlog(String query,Pageable pageable);

    //处理blog的文本内容转html
    Blog getAndHtml(Long id);

    //根据标签id分页查询博客
    Page<Blog> findByTagId(Long id,Pageable pageable);

    //归档功能 根据年份来查询博客集合对象
    Map<String,List<Blog>> archiveBlog();

    //查询博客的总记录数
    Long countBlog();

}

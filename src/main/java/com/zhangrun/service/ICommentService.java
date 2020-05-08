package com.zhangrun.service;

import com.zhangrun.entity.Comment;

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/6 14:18
 */
public interface ICommentService {
    //根据博客id查询评论对象
    List<Comment> listCommentByBlogId(Long blogId);

    //保存功能
    Comment saveComment(Comment comment);
}

package com.zhangrun.dao;

import com.zhangrun.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/6 14:19
 */
public interface ICommentDao extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}

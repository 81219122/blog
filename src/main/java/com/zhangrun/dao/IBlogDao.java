package com.zhangrun.dao;

import com.zhangrun.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 10:23
 */
public interface IBlogDao extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    //根据博客是否发布来查询博客对象
    @Query("select b from Blog b where b.published = true ")
    Page<Blog> findByPublished(Pageable pageable);

    //博客首页搜索查询
    @Query("select b from Blog b where b.title like %?1% or b.content like %?1%")
    Page<Blog> findByQuery(String query,Pageable pageable);

    //更新浏览次数
    @Transactional
    @Modifying
    @Query("update Blog b set b.views =b.views+1 where b.id = ?1")
    int updateViews(Long id);

    /*
     * @Param
     * @return
     * function('date_format',b.updateTime,'%Y') mysql获取时间类型的年份这是jpa的语法
     * mysql语法 select date_format(b.updateTime,'%Y') as year from blog b Group by year Order by year desc
    */

    //根据博客查询出年份列表 只查询年份
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    //根据年份获取博客列表
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y')=?1 ")
    List<Blog> findByYear(String year);
}

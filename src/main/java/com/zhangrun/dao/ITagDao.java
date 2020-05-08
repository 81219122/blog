package com.zhangrun.dao;

import com.zhangrun.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 9:51
 */
public interface ITagDao extends JpaRepository<Tag,Long> {
    //根据标签名称查询标签对象
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}

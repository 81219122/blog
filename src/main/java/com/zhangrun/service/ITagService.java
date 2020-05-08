package com.zhangrun.service;

import com.zhangrun.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 9:53
 */
public interface ITagService {
    //新增标签
    Tag save(Tag tag);

    //根据id查询
    Tag getTag(Long id);

    //分页查询
    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

    Tag findByName(String name);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);
}

package com.zhangrun.service.impl;

import com.zhangrun.dao.ITagDao;
import com.zhangrun.entity.Tag;
import com.zhangrun.exception.NotFoundException;
import com.zhangrun.service.ITagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 9:53
 */
@Service
public class TagServiceImpl implements ITagService {
    @Autowired
    private ITagDao dao;
    @Override
    public Tag save(Tag tag) {
        return dao.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = dao.findById(id).get();
        if (tag1==null){
            throw new NotFoundException("标签对象不存在");
        }else {
            BeanUtils.copyProperties(tag,tag1);
        }
        return dao.save(tag1);
    }

    @Override
    public void deleteTag(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Tag findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return dao.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return dao.findAllById(getList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return dao.findTop(pageable);
    }

    /*字符串转list*/
    private List<Long> getList(String ids){
        if (ids==null) return null;
        String[] split = ids.split(",");
        List<Long> list=new ArrayList<>();
        for(String s :split){
            list.add(new Long(s));
        }
        return list;
    }
}

package com.zhangrun.service.impl;

import com.zhangrun.dao.ITypeDao;
import com.zhangrun.entity.Type;
import com.zhangrun.exception.NotFoundException;
import com.zhangrun.service.ITypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/2 9:46
 */
@Service
public class TypeServiceImpl implements ITypeService {
    @Autowired
    private ITypeDao dao;

    @Transactional  //放到事务里面
    @Override
    public Type save(Type type) {
        Type save = dao.save(type);
        return save;
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return dao.findById(id).get();
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return dao.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=dao.findById(id).get();
        if (t==null){
            throw new NotFoundException("不存在该类型");
        }
        //如果根据id查到了type对象 就把参数type 与t交换然后保存
        BeanUtils.copyProperties(type,t);

        return dao.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return dao.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=PageRequest.of(0,size,sort);
        return dao.findTop(pageable);
    }
}

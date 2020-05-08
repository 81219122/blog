package com.zhangrun.service;

import com.zhangrun.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/2 9:46
 */
public interface ITypeService {
    //新增分类
    Type save(Type type);

    //根据id查询
    Type getType(Long id);
    //分页查询
    Page<Type> listType(Pageable pageable);
    //修改分类
    Type updateType(Long id,Type type);
    //删除分类
    void deleteType(Long id);

    //根据分类名称查询分类
    Type getTypeByName(String name);

    List<Type> listType();

    //查询分类 根据你传入的值查询多少个对象
    List<Type> listTypeTop(Integer size);
}

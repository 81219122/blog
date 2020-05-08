package com.zhangrun.dao;

import com.zhangrun.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/2 9:49
 */
public interface ITypeDao extends JpaRepository<Type,Long> {
    //根据分类名称查询分类
    Type findByName(String name);


    @Query("select  t from Type t")
    List<Type> findTop(Pageable pageable);
}

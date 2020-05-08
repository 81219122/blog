package com.zhangrun.dao;

import com.zhangrun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 11:59
 */
public interface IUserDao extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}

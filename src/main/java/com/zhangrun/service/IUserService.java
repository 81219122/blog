package com.zhangrun.service;

import com.zhangrun.entity.User;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 11:57
 */
public interface IUserService {
    User checkUser(String username,String password);
}

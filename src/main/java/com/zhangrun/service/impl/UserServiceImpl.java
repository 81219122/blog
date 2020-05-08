package com.zhangrun.service.impl;

import com.zhangrun.dao.IUserDao;
import com.zhangrun.entity.User;
import com.zhangrun.service.IUserService;
import com.zhangrun.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 11:58
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Util.code(password));

        return user;
    }
}

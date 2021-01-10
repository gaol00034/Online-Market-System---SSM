package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.UserMapper;
import com.glt.po.User;
import com.glt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
}

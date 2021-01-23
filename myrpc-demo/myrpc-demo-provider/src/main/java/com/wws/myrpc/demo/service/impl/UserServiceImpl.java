package com.wws.myrpc.demo.service.impl;

import com.wws.myrpc.demo.domain.User;
import com.wws.myrpc.demo.service.UserService;

public class UserServiceImpl implements UserService {
    public User getById(Integer id) {
        return new User();
    }

    public boolean save(User user) {
        return false;
    }
}

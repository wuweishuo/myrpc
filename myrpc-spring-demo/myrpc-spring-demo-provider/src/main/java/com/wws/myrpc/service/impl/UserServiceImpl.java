package com.wws.myrpc.service.impl;

import com.wws.myrpc.domain.User;
import com.wws.myrpc.server.RpcService;
import com.wws.myrpc.service.UserService;

@RpcService
public class UserServiceImpl implements UserService {
    public User getById(Integer id) {
        return new User();
    }

    public boolean save(User user) {
        return false;
    }
}

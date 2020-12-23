package com.wws.myrpc.service.impl;


import com.wws.myrpc.domain.User;
import com.wws.myrpc.service.UserService;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User echo(User user) {
        return user;
    }

    @Override
    public void save(User user) {
        return;
    }

    @Override
    public List<User> list() {
        User user = new User();
        user.setId(1L);
        user.setName("list");
        return Collections.singletonList(user);
    }

    @Override
    public User[] array() {
        User user = new User();
        user.setId(1L);
        user.setName("list");
        return new User[]{user};
    }

    @Override
    public void saveArr(User[] users) {
        return;
    }

    @Override
    public void exception() throws Exception {
        throw new Exception("test.....");
    }

    @Override
    public User multiParam(long id, String name) {
        User user = new User();
        user.setName(name);
        user.setId(id);
        return user;
    }
}

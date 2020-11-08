package com.wws.myrpc.service;

import com.wws.myrpc.domain.User;

import java.util.List;

public interface UserService {

    User echo(User user);

    void save(User user);

    List<User> list();

    User[] array();

    void saveArr(User[] users);

    void exception() throws Exception;

    User multiParam(long id, String name);

}

package com.wws.myrpc.demo.service;

import com.wws.myrpc.demo.domain.User;

public interface UserService {

    User getById(Integer id);

    boolean save(User user);

}

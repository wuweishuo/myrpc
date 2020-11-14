package com.wws.myrpc.service;

import com.wws.myrpc.domain.User;

public interface UserService {

    User getById(Integer id);

    boolean save(User user);

}

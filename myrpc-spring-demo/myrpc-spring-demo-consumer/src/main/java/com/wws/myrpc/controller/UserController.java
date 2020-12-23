package com.wws.myrpc.controller;

import com.wws.myrpc.client.UserClient;
import com.wws.myrpc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Integer id) {
        return userClient.getById(id);
    }

    @PostMapping
    public boolean save(User user) {
        return userClient.save(user);
    }

}

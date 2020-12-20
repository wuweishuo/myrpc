package com.wws.myrpc.client;

import com.wws.myrpc.service.UserService;

@RpcClient(name = "user-service", registerUrl = "127.0.0.1:2181")
public interface UserClient extends UserService {
}

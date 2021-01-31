package com.wws.myrpc.client;

import com.wws.myrpc.service.UserService;

@RpcClient(name = "user-service", registryName = "nacos", registerUrl = "127.0.0.1:8848")
public interface UserClient extends UserService {
}

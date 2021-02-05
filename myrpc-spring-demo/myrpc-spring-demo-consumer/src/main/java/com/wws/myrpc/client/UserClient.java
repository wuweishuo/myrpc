package com.wws.myrpc.client;

import com.wws.myrpc.service.UserService;

@RpcClient(name = "user-service")
public interface UserClient extends UserService {
}

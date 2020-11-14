package com.wws.myrpc.client;

import com.wws.myrpc.service.UserService;

@RpcClient(ip = "127.0.0.1", port = 9000)
public interface UserClient extends UserService {
}

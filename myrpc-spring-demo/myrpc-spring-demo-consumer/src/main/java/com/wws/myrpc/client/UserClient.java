package com.wws.myrpc.client;

import com.wws.myrpc.service.UserService;

@RpcClient(name = "user-service", registryName = "zookeeper", registerUrl = "127.0.0.1:2181", clusterName = "failOver", loadBalanceName = "roundRobin")
public interface UserClient extends UserService {
}

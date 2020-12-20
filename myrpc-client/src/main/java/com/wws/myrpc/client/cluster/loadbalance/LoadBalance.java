package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;

public interface LoadBalance {

    ServerInfo select(List<ServerInfo> list);

}

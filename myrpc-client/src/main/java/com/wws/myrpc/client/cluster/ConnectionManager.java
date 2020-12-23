package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.registry.ServerInfo;

import java.util.List;

public interface ConnectionManager {

    Client getClient(ServerInfo serverInfo);

    void refresh(List<ServerInfo> serverInfoList);

    void shutdown();

}

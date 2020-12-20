package com.wws.myrpc.registry;

import java.util.List;

public interface NotifyListener {

    void notify(List<ServerInfo> serverInfoList);

    String name();

}

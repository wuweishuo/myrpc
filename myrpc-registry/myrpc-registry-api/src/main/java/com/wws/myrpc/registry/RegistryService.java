package com.wws.myrpc.registry;

public interface RegistryService {

    void connect(String addr) throws Exception;

    void register(ServerInfo serverInfo) throws Exception;

    void unregister(ServerInfo serverInfo) throws Exception;

    void subscribe(String name, NotifyListener notifyListener);

    void unsubscribe(String name, NotifyListener notifyListener);

}

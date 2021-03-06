package com.wws.myrpc.registry.nasco;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.wws.myrpc.registry.NotifyListener;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.ServerInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * NascoRegistryService
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public class NacosRegistryService implements RegistryService {

    private final NamingService naming;

    private final String url;

    public NacosRegistryService(String url) throws NacosException {
        this.url = url;
        this.naming = NamingFactory.createNamingService(url);
    }

    @Override
    public void destroy() throws Exception {
        naming.shutDown();
    }

    @Override
    public void register(ServerInfo serverInfo) throws Exception {
        naming.registerInstance(serverInfo.getName(), convert(serverInfo));
    }

    @Override
    public void unregister(ServerInfo serverInfo) throws Exception {
        naming.deregisterInstance(serverInfo.getName(), serverInfo.getIp(), serverInfo.getPort());
    }

    @Override
    public void subscribe(String name, NotifyListener notifyListener) {
        try {
            naming.subscribe(name, event -> {
                if (event instanceof NamingEvent) {
                    NamingEvent namingEvent = (NamingEvent) event;
                    List<Instance> instances = namingEvent.getInstances();
                    List<ServerInfo> serverInfos = instances.stream().map(this::convert).collect(Collectors.toList());
                    notifyListener.notify(serverInfos);
                }
            });
        } catch (NacosException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void unsubscribe(String name, NotifyListener notifyListener) {
        try {
            naming.unsubscribe(name, event -> {
            });
        } catch (NacosException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getUrl() {
        return url;
    }

    private ServerInfo convert(Instance instance) {
        ServerInfo serverInfo = new ServerInfo(instance.getServiceName(), instance.getIp(), instance.getPort(), instance.isEnabled());
        Map<String, String> metadata = instance.getMetadata();
        for (String key : metadata.keySet()) {
            serverInfo.setProperty(key, metadata.get(key));
        }
        return serverInfo;
    }

    private Instance convert(ServerInfo serverInfo) {
        Instance instance = new Instance();
        instance.setIp(serverInfo.getIp());
        instance.setPort(serverInfo.getPort());
        Map<String, String> instanceMeta = new HashMap<>(serverInfo.getMetaData());
        instance.setMetadata(instanceMeta);
        return instance;
    }
}

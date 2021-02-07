package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.instance.SimpleClient;
import com.wws.myrpc.registry.ServerInfo;
import com.wws.myrpc.serialize.SerializerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ConnectionManagerImpl
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ConnectionManagerImpl implements ConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerImpl.class);

    private HashMap<String, Client> clientMap = new HashMap<>();

    public Client getClient(ServerInfo serverInfo) {
        return clientMap.get(serverInfo.getUniqueKey());
    }

    public void refresh(List<ServerInfo> serverInfoList) {
        Map<String, ServerInfo> serverInfoMap = serverInfoList.stream().collect(Collectors.toMap(ServerInfo::getUniqueKey, Function.identity()));
        // 关闭已经与注册中心失去联系的client
        for (String key : clientMap.keySet()) {
            if (!serverInfoMap.containsKey(key)) {
                Client client = clientMap.get(key);
                try {
                    client.shutdown();
                } catch (Exception e) {
                    logger.error("{} shutdown err:{}", client, e);
                }
                clientMap.remove(key);
            }
        }
        // 创建新发现的client
        for (String key : serverInfoMap.keySet()) {
            if (!clientMap.containsKey(key)) {
                ServerInfo serverInfo = serverInfoMap.get(key);
                SimpleClient simpleClient = new SimpleClient(serverInfo.getIp(), serverInfo.getPort(), getSerializerProperties(serverInfo));
                try {
                    simpleClient.connect();
                    clientMap.putIfAbsent(key, simpleClient);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        for (Client client : clientMap.values()) {
            try {
                client.shutdown();
            } catch (Exception e) {
                logger.error("{} shutdown err:{}", client, e);
            }
        }
        clientMap = new HashMap<>();
    }

    private SerializerProperties getSerializerProperties(ServerInfo serverInfo){
        // 给个默认值，会从serverInfo中覆盖
        SerializerProperties serializerProperties = new SerializerProperties("jdk");
        serializerProperties.clone(serverInfo.getMetaData());
        return serializerProperties;
    }

}

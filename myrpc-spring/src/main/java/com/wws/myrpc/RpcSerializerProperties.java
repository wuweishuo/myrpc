package com.wws.myrpc;

import com.wws.myrpc.client.cluster.ClusterProperties;
import com.wws.myrpc.serialize.SerializerProperties;

import java.util.Map;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public class RpcSerializerProperties {

    private String name;

    private Map<String, String> props;

    public SerializerProperties toProperties(){
        SerializerProperties properties = new SerializerProperties(name);
        properties.setProperties(props);
        return properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }
}

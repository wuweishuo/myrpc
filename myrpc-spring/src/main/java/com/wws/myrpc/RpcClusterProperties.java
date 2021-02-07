package com.wws.myrpc;

import com.wws.myrpc.client.cluster.ClusterProperties;

import java.util.Map;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public class RpcClusterProperties {

    private String name;

    private Map<String, String> props;

    public ClusterProperties toProperties(){
        ClusterProperties properties = new ClusterProperties(name);
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

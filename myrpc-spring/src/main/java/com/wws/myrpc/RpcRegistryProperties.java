package com.wws.myrpc;

import com.wws.myrpc.registry.RegistryProperties;

import java.util.Map;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public class RpcRegistryProperties {

    private String name;

    private String url;

    private Map<String, String> props;

    public RegistryProperties toProperties() {
        RegistryProperties properties = new RegistryProperties(name, url);
        properties.setProperties(props);
        return properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }
}

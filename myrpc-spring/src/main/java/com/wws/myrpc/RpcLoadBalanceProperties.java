package com.wws.myrpc;

import com.wws.myrpc.client.cluster.loadbalance.LoadBalanceProperties;

import java.util.Map;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public class RpcLoadBalanceProperties {

    private String name;

    private Map<String, String> props;

    public LoadBalanceProperties toProperties(){
        return new LoadBalanceProperties(name);
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

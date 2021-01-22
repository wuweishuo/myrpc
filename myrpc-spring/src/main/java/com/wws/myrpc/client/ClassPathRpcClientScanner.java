package com.wws.myrpc.client;

import com.wws.myrpc.client.cluster.ClusterProperties;
import com.wws.myrpc.client.instance.SimpleClientProperties;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

public class ClassPathRpcClientScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathRpcClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        processBeanDefinition(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    public void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));
    }

    private void processBeanDefinition(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            AnnotationMetadata metadata = beanDefinition.getMetadata();
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(RpcClient.class.getCanonicalName());
            assert annotationAttributes != null;
            String ip = (String) annotationAttributes.get("ip");
            Integer port = (Integer) annotationAttributes.get("port");
            String serializerName = (String) annotationAttributes.get("serializerName");

            ClientProperties properties;
            if(StringUtils.isEmpty(ip) || port == null){
                String name = (String) annotationAttributes.get("name");
                String registerUrl = (String) annotationAttributes.get("registerUrl");
                String registryName = (String) annotationAttributes.get("registryName");
                String clusterName = (String) annotationAttributes.get("clusterName");
                String loadBalanceName = (String) annotationAttributes.get("loadBalanceName");
                properties = new ClusterProperties(name, clusterName, loadBalanceName, registryName, registerUrl, serializerName);
            }else{
                properties = new SimpleClientProperties(ip, port, serializerName);
            }

            beanDefinition.getPropertyValues().add("properties", properties);
            beanDefinition.getPropertyValues().add("clientClass", beanDefinition.getBeanClassName());
            beanDefinition.getPropertyValues().add("proxyFactory", new RuntimeBeanReference("rpcClientProxyFactory"));
            beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinition.setBeanClass(RpcClientFactoryBean.class);
            beanDefinition.setFactoryBeanName("");
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

}

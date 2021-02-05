package com.wws.myrpc.client;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

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
            String name = (String) annotationAttributes.get("name");
            String clusterBean = (String) annotationAttributes.get("clusterBean");
            String loadBalanceBean = (String) annotationAttributes.get("loadBalanceBean");
            String serializerBean = (String) annotationAttributes.get("serializerBean");
            String registryBean = (String) annotationAttributes.get("registryBean");

            beanDefinition.getPropertyValues().add("clusterBean", clusterBean);
            beanDefinition.getPropertyValues().add("loadBalanceBean", loadBalanceBean);
            beanDefinition.getPropertyValues().add("serializerBean", serializerBean);
            beanDefinition.getPropertyValues().add("registryBean", registryBean);
            beanDefinition.getPropertyValues().add("ip", ip);
            beanDefinition.getPropertyValues().add("port", port);
            beanDefinition.getPropertyValues().add("name", name);
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

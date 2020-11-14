package com.wws.myrpc.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class RpcServiceBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private Server server;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        RpcService annotation = clazz.getAnnotation(RpcService.class);
        if(annotation != null){
            System.out.println("register service:"+clazz.getName());
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                server.registerService(anInterface, bean);
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.server = applicationContext.getBean(Server.class);
    }
}

import com.wws.myrpc.client.RpcClientFactoryBean;
import com.wws.myrpc.client.UserClient;
import com.wws.myrpc.client.proxy.JdkProxyFactory;
import com.wws.myrpc.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringUtils;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder1 = BeanDefinitionBuilder.genericBeanDefinition(JdkProxyFactory.class);
        beanFactory.registerBeanDefinition("rpcClientProxyFactory", beanDefinitionBuilder1.getBeanDefinition());

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RpcClientFactoryBean.class);
        beanDefinitionBuilder.addPropertyValue("ip", "127.0.0.1");
        beanDefinitionBuilder.addPropertyValue("port", 9000);
        beanDefinitionBuilder.addPropertyValue("clientClazz", UserClient.class.getName());
        beanDefinitionBuilder.addPropertyValue("proxyFactory", new RuntimeBeanReference("rpcClientProxyFactory"));
        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, UserClient.class.getName());
        beanFactory.registerBeanDefinition("userClient", beanDefinition);
        System.out.println(beanFactory.getBean(BeanFactory.FACTORY_BEAN_PREFIX+"userClient"));
        System.out.println(beanFactory.getBean("userClient"));
        UserClient bean = beanFactory.getBean(UserClient.class);
    }

    @Test
    public void test2(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder1 = BeanDefinitionBuilder.genericBeanDefinition(JdkProxyFactory.class);
        beanFactory.registerBeanDefinition("rpcClientProxyFactory", beanDefinitionBuilder1.getBeanDefinition());

        String className = UserClient.class.getName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(RpcClientFactoryBean.class);
        definition.addPropertyValue("ip", "127.0.0.1");
        definition.addPropertyValue("port", 9000);
        definition.addPropertyValue("clientClazz", className);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.addPropertyValue("proxyFactory", new RuntimeBeanReference("rpcClientProxyFactory"));

        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, className);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, beanFactory);

        System.out.println(beanFactory.getBean(UserClient.class));
    }

    @Test
    public void test3(){
        JdkProxyFactory jdkProxyFactory = new JdkProxyFactory();
        UserService proxy = jdkProxyFactory.getProxy(null, UserService.class);
        System.out.println(proxy instanceof UserService);

    }

}

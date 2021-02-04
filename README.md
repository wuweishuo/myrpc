# myrpc
  在阅读dubbo源码之后，产生自己手写一个rpc框架的念头，于是有了这个myrpc项目，实现过程中借鉴了dubbo的思想，希望可以对学习dubbo等rpc框架的人产生一点帮助。

## Features

- [x] 基于netty的rpc通信
- [x] 支持多种序列化方式，目前仅支持jdk, fst, kyro等
- [x] 支持多种注册中心，目前仅支持zookeeper, nacos等
- [x] 支持多种故障转移策略：目前仅支持failfast，failover，failback等
- [x] 支持多种负载均衡策略，目前仅支持random，roundRobin等
- [x] 对上述功能支持spi扩展
- [ ] 支持filter扩展点
- [x] 集成spring

## Use 

1. 引入maven依赖

```xml
<dependency>
  <groupId>com.wws</groupId>
  <artifactId>myrpc-all</artifactId>
  <version>${project.version}</version>
</dependency>
```

2. 服务提供者

```java
public class Provider {
    public static void main(String[] args) throws Exception {
        Server server = new Server(9000);
        server.start();
        UserService userService = new UserServiceImpl();
        server.registerService(UserService.class, userService);
    }
}
```

3. 服务消费者

```java
public class Consumer {
    public static void main(String[] args) throws Throwable {
        Client client = new SimpleClient("127.0.0.1", 9000);
        client.connect();
        Class<UserService> clazz = UserService.class;
        Method getById = clazz.getMethod("getById", Integer.class);
        User user = client.transport(getById, User.class, 1);
        System.out.println(user);
        client.shutdown();
    }
}
```

## Use in Spring

1. 引入依赖

```xml
<dependency>
  <groupId>com.wws</groupId>
  <artifactId>myrpc-spring</artifactId>
  <version>${myrpc.version}</version>
</dependency>
```

2. 服务提供者

```java
@SpringBootApplication
@EnableRpcServer
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

@RpcService
public class UserServiceImpl implements UserService {
    public User getById(Integer id) {
        return new User();
    }

    public boolean save(User user) {
        return false;
    }
}
```

3. 服务消费者

```java
@SpringBootApplication
@RpcClientScan(basePackages = "com.wws.myrpc.client")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}

@RpcClient(name = "user-service", registerUrl = "127.0.0.1:2181")
public interface UserClient extends UserService {
}
```


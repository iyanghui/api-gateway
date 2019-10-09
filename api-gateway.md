#### 1. 网关核心(api-gateway-core)

**ZuulServlet中有3个核心的方法**，它们分别是：preRoute(),route(), postRoute()。Zuul对所有request的处理逻辑都在这三个方法里面，而这些方法分别对应了Zuul中定义的几种标准过滤器类型

pre：实现身份认证等前置逻辑

route：用于实现Gateway到内部微服务调用的路由、负载均衡、限流等功能

post：用来为响应添加标准的Http Header、收集统计信息和指标，并将响应从微服务发送给客户端；



##### 1.1 鉴权

**Q**：jwt和oAuth2

**A**：为了简单，快速完成demo制作，使用jwt进行token生成和校验



**Q**：怎么判断哪些不需要验签(例如：登录)

**A**：插件化形式，filters



##### 1.3 接口限流

- 接口调用怎么存储？
- 令牌桶
- Redis存储
- 



##### 1.3 路由

使用配置文件形式：url -> service



- 

  



##### 1.4 协议转换





#### 2. 网关控制台(api-gateway-admin)



#### 3. 网关监控(api-gateway-monitor)


#### 1. 网关核心(api-gateway-core)





preFilter -> routeFilter -> postFilter





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


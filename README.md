# 网关核心(api-gateway-core)
> 本项目是在使用[zuu](https://github.com/Netflix/zuul)过程中参照其进行实现的，更多完整功能请前往[zuu;](https://github.com/Netflix/zuul)。
>

**`api-gateway-core`**和`zuul`类似，(对外)核心由三类`filter`组成：
- pre：实现身份认证等前置逻辑
- route：用于实现Gateway到内部微服务调用的路由、负载均衡、限流等功能
- post：用来为响应添加标准的Http Header、收集统计信息和指标，并将响应从微服务发送给客户端；
请求处理逻辑都在这三类`filter`里面完成，业务可以通过实现`AbsFilter`完成扩展。


## 鉴权
**TODO**

## 限流
内置限流容器，按`service`分隔。

## 路由
路由(服务)发现功能由[lego:srd](https://github.com/iyanghui/service-center)提供。



## 协议转换
**TODO**


## 容错
**TODO**


# 网关控制台(api-gateway-admin)
**TODO**


# 网关监控(api-gateway-monitor)
**TODO**

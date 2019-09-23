package pers.zhixilang.yway.core.filter;

import org.springframework.stereotype.Component;
import pers.zhixilang.yway.core.config.RouteConfig;
import pers.zhixilang.yway.core.cons.FilterTypeEnum;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.exception.ServiceNotFoundException;

import javax.annotation.Resource;

/**
 * 上下文封装
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:33
 */
@Component
public class ContextBuildFilter extends AbsWayFilter {

    @Resource
    private RouteConfig routeConfig;

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.name();
    }

    @Override
    public int filterOrder() {
        return 9999;
    }

    @Override
    public void run() {
        RequestContext context = RequestContext.getContext();
        String uri = context.getRequest().getRequestURI();
        String path = routeConfig.getServiceName(uri);
        if (null == path || "".equals(path)) {
            throw new ServiceNotFoundException("服务未找到，请检查配置文件！uri = ["+ uri +"]");
        }
        context.setServiceName(path);
        context.setServiceUrl(routeConfig.getServiceUrl(uri));
    }
}

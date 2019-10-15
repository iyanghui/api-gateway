package pers.zhixilang.yway.core.filter;

import org.springframework.stereotype.Component;
import pers.zhixilang.service.core.Constants;
import pers.zhixilang.service.core.RouteManage;
import pers.zhixilang.yway.core.cons.FilterTypeEnum;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.exception.RouteNotFoundException;

/**
 * 上下文封装
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:33
 */
@Component
public class ContextBuildFilter extends AbsWayFilter {

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
        String routeUrl = RouteManage.getRoute(uri);
        if ("".equals(routeUrl)) {
            throw new RouteNotFoundException("服务未找到，请检查服务列表！url = ["+ uri +"]");
        }
        context.setRoutePrefix(routeUrl.split(Constants.SEPARATOR_ROUTE_URL)[0]);
        context.setRouteUrl(routeUrl.split(Constants.SEPARATOR_ROUTE_URL)[1]);
    }
}

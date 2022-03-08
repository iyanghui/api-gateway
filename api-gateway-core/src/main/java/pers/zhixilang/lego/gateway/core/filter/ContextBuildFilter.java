package pers.zhixilang.lego.gateway.core.filter;

import pers.zhixilang.lego.gateway.core.enums.FilterTypeEnum;
import pers.zhixilang.lego.gateway.core.exception.RouteNotFoundException;
import pers.zhixilang.lego.gateway.core.runner.RequestContext;
import pers.zhixilang.lego.srd.core.RouteManage;

import java.util.Random;
import java.util.Set;

/**
 * 上下文封装
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:33
 */
public class ContextBuildFilter extends AbsFilter {

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

        Set<String> routes = RouteManage.getRoutes(uri);
        if (null == routes || routes.size() == 0) {
            throw new RouteNotFoundException("服务未找到，请检查服务列表！url = ["+ uri +"]");
        }
        // simple load balance
        String routeUrl = routes.toArray()[new Random().nextInt(routes.size())].toString();

        context.setRoutePrefix(uri);
        context.setRouteUrl(routeUrl);
    }

}

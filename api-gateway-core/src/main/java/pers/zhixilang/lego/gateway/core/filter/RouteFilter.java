package pers.zhixilang.lego.gateway.core.filter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.zhixilang.lego.gateway.core.enums.FilterTypeEnum;
import pers.zhixilang.lego.gateway.core.runner.RequestContext;
import pers.zhixilang.lego.gateway.core.util.LimiterManager;

import javax.annotation.Resource;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 16:35
 */
public class RouteFilter extends AbsFilter {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterTypeEnum.ROUTE.name();
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public void run() {
        RequestContext context = RequestContext.getContext();

        try {
            // TODO use lego:rpc
            ResponseEntity responseEntity = restTemplate.exchange(context.getRouteUrl(), null, context.getRequestEntity(),
                    byte[].class);
            context.setResponseEntity(responseEntity);
        } finally {
            LimiterManager.supplement(context.getRoutePrefix());
        }
    }
}

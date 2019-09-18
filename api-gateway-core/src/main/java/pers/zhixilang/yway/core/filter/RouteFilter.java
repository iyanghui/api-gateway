package pers.zhixilang.yway.core.filter;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.zhixilang.yway.core.context.RequestContext;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 16:35
 */
public class RouteFilter extends AbsWayFilter {

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public void run() {
        RequestContext context = RequestContext.getContext();
        RequestEntity requestEntity = context.getRequestEntity();

        RestTemplate restTemplate = new RestTemplate();

        // TODO 通信框架用netty
        ResponseEntity responseEntity = restTemplate.exchange(requestEntity, byte[].class);
        context.setResponseEntity(responseEntity);
    }

}

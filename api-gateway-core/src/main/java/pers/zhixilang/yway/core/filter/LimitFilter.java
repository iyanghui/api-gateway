package pers.zhixilang.yway.core.filter;

import org.springframework.stereotype.Component;
import pers.zhixilang.yway.core.cons.FilterTypeEnum;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.exception.ServiceUnableException;
import pers.zhixilang.yway.core.limit.LimiterManager;

import javax.annotation.Resource;

/**
 * 服务限流
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:29
 */
@Component
public class LimitFilter extends AbsWayFilter{

    @Resource
    private LimiterManager limiterManager;

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.name();
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public void run() {
        RequestContext context = RequestContext.getContext();
        String serviceName = context.getServiceName();
        boolean isAllow = limiterManager.acquire(serviceName);
        if (!isAllow) {
            throw new ServiceUnableException("服务["+ serviceName +"]暂不可用，请稍后再试！");
        }
    }
}

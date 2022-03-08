package pers.zhixilang.lego.gateway.core.filter;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import pers.zhixilang.lego.gateway.core.enums.FilterTypeEnum;
import pers.zhixilang.lego.gateway.core.exception.ServiceUnableException;
import pers.zhixilang.lego.gateway.core.runner.RequestContext;
import pers.zhixilang.lego.gateway.core.util.LimiterManager;

/**
 * 服务限流
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:29
 */
public class LimitFilter extends AbsFilter implements InitializingBean, DisposableBean {

    @Override
    public String filterType() {
        return FilterTypeEnum.ROUTE.name();
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public void run() {
        RequestContext context = RequestContext.getContext();
        String serviceName = context.getRoutePrefix();
        boolean isAllow = LimiterManager.acquire(serviceName);
        if (!isAllow) {
            throw new ServiceUnableException("服务["+ serviceName +"]暂不可用，请稍后再试！");
        }
    }

    @Override
    public void destroy() throws Exception {
        LimiterManager.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LimiterManager.init();
    }
}

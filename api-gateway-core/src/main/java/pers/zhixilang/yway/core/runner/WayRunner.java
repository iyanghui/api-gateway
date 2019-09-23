package pers.zhixilang.yway.core.runner;

import org.springframework.stereotype.Component;
import pers.zhixilang.yway.core.comparator.FilterComparator;
import pers.zhixilang.yway.core.cons.FilterTypeEnum;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.filter.AbsWayFilter;
import pers.zhixilang.yway.core.filter.ContextBuildFilter;
import pers.zhixilang.yway.core.filter.LimitFilter;
import pers.zhixilang.yway.core.filter.RequestWrapperFilter;
import pers.zhixilang.yway.core.filter.RouteFilter;
import pers.zhixilang.yway.core.filter.SendResponseFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 执行器
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 17:06
 */
@Component
public class WayRunner {

    /**
     * 过滤器组
     */
    private ConcurrentHashMap<String, List<AbsWayFilter>> filterMap;

    @Resource
    private ContextBuildFilter contextBuildFilter;

    @Resource
    private LimitFilter limitFilter;

    @Resource
    private RequestWrapperFilter requestWrapperFilter;

    @Resource
    private RouteFilter routeFilter;

    @Resource
    private SendResponseFilter sendResponseFilter;

    @PostConstruct
    public void init() {

        // TODO 动态读取配置，自定义注解
        List<AbsWayFilter> preFilters = new ArrayList<AbsWayFilter>(){{
            add(contextBuildFilter);
            add(limitFilter);
            add(requestWrapperFilter);
        }};

        List<AbsWayFilter> routeFilters = new ArrayList<AbsWayFilter>(){{
            add(routeFilter);
        }};

        List<AbsWayFilter> postFilters = new ArrayList<AbsWayFilter>(){{
            add(sendResponseFilter);
        }};

        FilterComparator filterComparator = new FilterComparator();

        preFilters.sort(filterComparator);
        routeFilters.sort(filterComparator);
        postFilters.sort(filterComparator);

        filterMap = new ConcurrentHashMap<String, List<AbsWayFilter>>() {
            {
                put(FilterTypeEnum.PRE.name(), preFilters);
                put(FilterTypeEnum.ROUTE.name(), routeFilters);
                put(FilterTypeEnum.POST.name(), postFilters);
            }
        };
    }


    public void init(HttpServletRequest request, HttpServletResponse response) {
        RequestContext context = RequestContext.getContext();
        context.setRequest(request);
        context.setResponse(response);
    }

    public void preRoute() {
        runFilters(FilterTypeEnum.PRE.name());
    }

    public void route() {
        runFilters(FilterTypeEnum.ROUTE.name());
    }

    public void postRoute() {
        runFilters(FilterTypeEnum.POST.name());
    }

    private void runFilters(String filterType) {
        List<AbsWayFilter> filters = filterMap.get(filterType);
        if (null == filters || filters.size() == 0) {
            return;
        }
        for (AbsWayFilter filter: filters) {
            filter.run();
        }
    }

}

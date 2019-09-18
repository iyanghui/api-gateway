package pers.zhixilang.yway.core.runner;

import pers.zhixilang.yway.core.comparator.FilterComparator;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.filter.AbsWayFilter;
import pers.zhixilang.yway.core.filter.RequestWrapperFilter;
import pers.zhixilang.yway.core.filter.RouteFilter;
import pers.zhixilang.yway.core.filter.SendResponseFilter;

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
public class WayRunner {

    private ConcurrentHashMap<String, List<AbsWayFilter>> filterMap;


    public WayRunner() {

        // TODO 改为动态读取配置
        List<AbsWayFilter> preFilters = new ArrayList<AbsWayFilter>(){{
            add(new RequestWrapperFilter());
        }};

        List<AbsWayFilter> routeFilters = new ArrayList<AbsWayFilter>(){{
            add(new RouteFilter());
        }};

        List<AbsWayFilter> postFilters = new ArrayList<AbsWayFilter>(){{
            add(new SendResponseFilter());
        }};

        FilterComparator filterComparator = new FilterComparator();

        preFilters.sort(filterComparator);
        routeFilters.sort(filterComparator);
        postFilters.sort(filterComparator);

        filterMap = new ConcurrentHashMap<String, List<AbsWayFilter>>() {
            {
                put("pre", preFilters);
                put("route", routeFilters);
                put("post", postFilters);
            }
        };
    }


    public void init(HttpServletRequest request, HttpServletResponse response) {
        RequestContext context = RequestContext.getContext();
        context.setRequest(request);
        context.setResponse(response);
    }

    public void preRoute() {
        runFilters("pre");
    }

    public void route() {
        runFilters("route");
    }

    public void postRoute() {
        runFilters("post");
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

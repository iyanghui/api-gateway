package pers.zhixilang.yway.core.runner;

import org.springframework.stereotype.Component;
import pers.zhixilang.yway.core.comparator.FilterComparator;
import pers.zhixilang.yway.core.cons.FilterTypeEnum;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.filter.AbsWayFilter;

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
    private List<AbsWayFilter> filterList;

    @PostConstruct
    public void init() {
        List<AbsWayFilter> preFilters = new ArrayList<>();
        List<AbsWayFilter> routeFilters = new ArrayList<>();
        List<AbsWayFilter> postFilters = new ArrayList<>();

        for (AbsWayFilter filter: filterList) {
            if (filter.filterType().equals(FilterTypeEnum.PRE.name())) {
                preFilters.add(filter);
            } else if (filter.filterType().equals(FilterTypeEnum.ROUTE.name())) {
                routeFilters.add(filter);
            } else if (filter.filterType().equals(FilterTypeEnum.POST.name())) {
                postFilters.add(filter);
            }
        }

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

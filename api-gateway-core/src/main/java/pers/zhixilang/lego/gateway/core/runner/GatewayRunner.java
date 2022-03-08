package pers.zhixilang.lego.gateway.core.runner;

import pers.zhixilang.lego.gateway.core.enums.FilterTypeEnum;
import pers.zhixilang.lego.gateway.core.filter.AbsFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 执行器
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 17:06
 */
public class GatewayRunner {

    /**
     * 过滤器组
     */
    private ConcurrentHashMap<String, List<AbsFilter>> filterMap;

    @Resource
    private List<AbsFilter> filterList;

    @PostConstruct
    public void init() {
        List<AbsFilter> preFilters = new ArrayList<>();
        List<AbsFilter> routeFilters = new ArrayList<>();
        List<AbsFilter> postFilters = new ArrayList<>();

        for (AbsFilter filter: filterList) {
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

        filterMap = new ConcurrentHashMap<String, List<AbsFilter>>() {
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
        List<AbsFilter> filters = filterMap.get(filterType);
        if (null == filters || filters.size() == 0) {
            return;
        }
        for (AbsFilter filter: filters) {
            filter.run();
        }
    }

    static class FilterComparator implements Comparator<AbsFilter> {

        @Override
        public int compare(AbsFilter o1, AbsFilter o2) {
            return o2.filterOrder() - o1.filterOrder();
        }
    }

}

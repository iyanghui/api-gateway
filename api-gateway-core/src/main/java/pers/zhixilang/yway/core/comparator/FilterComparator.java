package pers.zhixilang.yway.core.comparator;

import pers.zhixilang.yway.core.filter.AbsWayFilter;

import java.util.Comparator;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-17 18:54
 */
public class FilterComparator implements Comparator<AbsWayFilter> {

    @Override
    public int compare(AbsWayFilter o1, AbsWayFilter o2) {
        return o2.filterOrder() - o1.filterOrder();
    }
}

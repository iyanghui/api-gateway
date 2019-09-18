package pers.zhixilang.yway.core.filter;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 16:34
 */
public abstract class AbsWayFilter {
    abstract public String filterType();

    abstract public int filterOrder();

    abstract public void run();
}

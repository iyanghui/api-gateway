package pers.zhixilang.yway.core.serviceregistry.discover;

import pers.zhixilang.yway.core.serviceregistry.entity.Route;
import pers.zhixilang.yway.core.serviceregistry.util.NamedThreadFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-03 11:41
 */
public class RedisDiscover {

    private ScheduledExecutorService discoverExecutor;

    private String url;

    private List<Route> routes;

    public RedisDiscover(String url) {
        this.url = url;
        discoverExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("service-discover-retry-expire-timer", true));
        discoverExecutor.scheduleWithFixedDelay(() -> {
            //
        }, 0, 0, TimeUnit.MILLISECONDS);
    }

    public static String getRoute(String prefix) {
        return "";
    }


}

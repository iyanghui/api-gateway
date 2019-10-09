package pers.zhixilang.yway.core.serviceregistry.registry;

import pers.zhixilang.yway.core.serviceregistry.entity.Route;
import pers.zhixilang.yway.core.serviceregistry.util.NamedThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-02 19:51
 */
public class RedisRegistry {
    private final ScheduledExecutorService expireExecutor;

    /**
     * 到期周期
     */
    private static final long EXPIRE_PERIOD = 60000L;

    private String url;

    private Route route;

    public RedisRegistry(String url, Route route) {
        this.url = url;
        this.route = route;
        expireExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("service-registry-retry-expire-timer", true));

        expireExecutor.scheduleWithFixedDelay(() -> {
            // 重新设置key

        }, 0L, EXPIRE_PERIOD, TimeUnit.MILLISECONDS);
    }


    private void registry() {

    }
}

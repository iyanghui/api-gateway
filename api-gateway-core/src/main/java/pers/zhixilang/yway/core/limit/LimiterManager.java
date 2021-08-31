package pers.zhixilang.yway.core.limit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 限流管理器
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 15:18
 */
@Component
public class LimiterManager implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LimiterManager.class);

    /**
     * 限流容器
     * 不同服务分开限流
     *
     */
    private ConcurrentHashMap<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();

    private ExecutorService supplementPool;

    /**
     * 默认token
     */
    private int token = 20;

    /**
     * 获取token超时时间
     */
    private long timeout = 60 * 1000L;

    /**
     * 初始化限流容器
     * @param args args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-token-supplement-").build();
        supplementPool = new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
    }

    @PreDestroy
    public void destroy() {
        if (null == supplementPool) {
            return;
        }

        supplementPool.shutdown();

        try {
            if (supplementPool.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                supplementPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error("关闭线程池[supplementPool]异常：", e);
        }
    }

    /**
     * 申请token
     * @param key 服务名
     * @return 是否申请成功
     */
    public boolean acquire(String key) {
        RateLimiter limiter = limiterMap.get(key);
        if (null == limiter) {
            synchronized (this) {
                limiter = new RateLimiter(key, token, timeout);
                limiterMap.putIfAbsent(key, limiter);
            }
        }
        return limiter.acquire();
    }

    /**
     * 补充token
     * @param key 服务名
     */
    public void supplement(String key) {
        RateLimiter limiter = limiterMap.get(key);
        if (null != limiter) {
            limiter.supplement(supplementPool);
        }
    }

}

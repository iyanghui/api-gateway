package pers.zhixilang.lego.gateway.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 限流管理器
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 15:18
 */
public class LimiterManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LimiterManager.class);

    private static AtomicInteger threadIndex = new AtomicInteger(0);

    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 限流容器
     * 不同服务分开限流
     *
     */
    private static ConcurrentHashMap<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();

    private static ExecutorService supplementPool;

    /**
     * 默认token
     */
    private static int token = 20;

    /**
     * 获取token超时时间
     */
    private static long timeout = 60 * 1000L;

    /**
     * 初始化限流容器
     */
    public static void init() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName("lego.gateway.supplement-pool-" + threadIndex.getAndIncrement());
                return thread;
            }
        };
        supplementPool = new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
    }

    public static void destroy() {
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
    public static boolean acquire(String key) {
        RateLimiter limiter = limiterMap.get(key);
        if (null == limiter) {
            lock.lock();
            try {
                limiter = new RateLimiter(key, token, timeout);
                limiterMap.putIfAbsent(key, limiter);
            } finally {
                lock.unlock();
            }
        }
        return limiter.acquire();
    }

    /**
     * 补充token
     * @param key 服务名
     */
    public static void supplement(String key) {
        RateLimiter limiter = limiterMap.get(key);
        if (null != limiter) {
            limiter.supplement(supplementPool);
        }
    }

}

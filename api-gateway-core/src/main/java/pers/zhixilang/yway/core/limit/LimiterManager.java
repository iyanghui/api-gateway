package pers.zhixilang.yway.core.limit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
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
     * 初始化限流容器
     * @param args args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // TODO 使用配置文件
        int token = 20;
        String[] serviceNames = new String[]{"/api/user/", "/api/bill/"};
        long timeout = 30000;

        RateLimiter limiter;
        for (String serviceName: serviceNames) {
            limiter = limiterMap.get(serviceName);
            // 双重锁确保安全创建
            if (null == limiter) {
                synchronized (this) {
                    limiterMap.computeIfAbsent(serviceName, k -> new RateLimiter(serviceName, token, timeout));
                }
            }
        }

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-token-supplement").build();
        supplementPool = new ThreadPoolExecutor(serviceNames.length, serviceNames.length * token, 0L,
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
     * 恒定速率增加token
     */
    @Scheduled(cron="0/3 * * * * ? ")
    public void supplement() {
        limiterMap.values().forEach((limiter) -> limiter.supplement(supplementPool));
    }

    public boolean acquire(String key) {
        RateLimiter limiter = limiterMap.get(key);
        return limiter.acquire();
    }

    public void supplement(String key) {

    }
}

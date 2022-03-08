package pers.zhixilang.lego.gateway.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;

/**
 * 令牌桶限流
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 15:18
 */
public class RateLimiter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiter.class);

    /**
     * 桶名
     */
    private String key;

    /**
     * 当前令牌
     */
    private volatile int token;

    /**
     * 初始令牌
     */
    private int originToken;

    /**
     * 获取令牌等待时间
     */
    private long timeout;


    private static Unsafe unsafe;

    /**
     * token偏移量
     */
    private static final long valueOffset;

    private final Object lock = new Object();

    static {
        try {
            Class<?> clazz = Unsafe.class;
            Field field = clazz.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(clazz);
            valueOffset = unsafe.objectFieldOffset(RateLimiter.class.getDeclaredField("token"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public RateLimiter(String key, int token, long timeout) {
        this.key = key;
        this.token = token;
        this.originToken = token;
        this.timeout = timeout;
    }

    /**
     * 获取一个令牌
     * @return 是否获取成功
     */
    public boolean acquire() {
        int current = token;
        if (current <= 0) {
            // 保证在token已经用光的情况下，仍然有竞争的能力
            current = originToken;
        }

        long expect = timeout;
        long future = System.currentTimeMillis() + expect;

        while (current > 0) {
            if (compareAndSet(current, current - 1)) {
                LOGGER.info("桶[{}]消费后 -> token = [{}]", key, token);
                return true;
            }

            current = token;
            if (current <= 0 && expect > 0) {
                // 有效期内，等待通知
                synchronized (lock) {
                    try {
                        // 释放锁标志
                        lock.wait(expect);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                current = token;
                if (current <= 0) {
                    current = originToken;
                }
                expect = future - System.currentTimeMillis();
            }
        }
        return false;
    }

    /**
     * 补充令牌
     * @param executorService 执行线程池
     */
    public void supplement(ExecutorService executorService) {
        LOGGER.info("桶[{}]投token... -> [{}]", key, token);
        if (token >= originToken) {
            return;
        }
        compareAndSet(token, token + 1);
        executorService.execute(() -> {
            synchronized (lock) {
                lock.notifyAll();
                LOGGER.info("桶[{}]补充后 -> token = [{}]", key, token);
            }
        });
    }

    /**
     * 设置获取一个令牌后token
     * @param expect 当前令牌
     * @param update 获取一个令牌后
     * @return 是否设置成功
     */
    private boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }
}

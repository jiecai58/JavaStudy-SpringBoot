package com.study.threadPool;

import lombok.Data;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 线程池属性
 * </p>
 *
 * @author caijie
 * @since 2021/3/13 14:43
 */
@Data
public class ExecutorProperties {

    /**
     * 核心线程数,线程池保持ALIVE状态线程数
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors() >> 0b1;
    /**
     * 线程池最大线程数
     */
    private int maxPoolSize = Runtime.getRuntime().availableProcessors() << 0b1;
    /**
     * 队列大小
     */
    private int queueCapacity = 0b1 << 0b1010;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds = 0b1 << 0b1010;

    /**
     * 线程的名称前缀
     */
    private String threadNamePrefix = "taskExecutor-";

    /**
     * 拒绝策略{@link ThreadPoolExecutor.CallerRunsPolicy}
     */
    private String rejectionPolicyName = "CallerRunsPolicy";

}

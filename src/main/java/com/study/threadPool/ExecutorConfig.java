/*
 * Copyright 2018 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.study.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * <p>
 * 线程池配置
 * </p>
 *
 * @author caijie
 * @since 2021/3/13 14:43
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * 线程池中任务的等待时间
     */
    public static final int AWAIT_TERMINATION_SECONDS = 0b1 << 0b10000;

    /**
     * 构建线程池
     *
     * @param executorProperties ExecutorBaseProperties
     * @return {@link Executor}
     */
    public Executor buildExecutor(ExecutorProperties executorProperties) {

        MdcThreadPoolTaskExecutor executor = new MdcThreadPoolTaskExecutor();

        if (executorProperties.getCorePoolSize() == executorProperties.getMaxPoolSize()) {
            executorProperties.setKeepAliveSeconds(0);
        }

        executor.setThreadFactory(Executors.defaultThreadFactory());

        //设置核心线程数
        executor.setCorePoolSize(executorProperties.getCorePoolSize());

        //设置最大线程数
        executor.setMaxPoolSize(executorProperties.getMaxPoolSize());

        //设置队列大小
        executor.setQueueCapacity(executorProperties.getQueueCapacity());

        //设置线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());

        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);

        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);

        //设置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(executorProperties.getThreadNamePrefix());

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        RejectedExecutionHandler rejectedExecutionHandler = null;

        try {
            Class<?> clazz = Class
                    .forName("java.util.concurrent.ThreadPoolExecutor$" + executorProperties.getRejectionPolicyName());
            rejectedExecutionHandler = (RejectedExecutionHandler) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error("获取rejection-policy异常，请查看配置文件", e);
            return null;
        }
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        //执行初始化
        executor.initialize();

        return executor;
    }

    /**
     * 这是{@link ThreadPoolTaskExecutor}的一个简单替换，可以在每个任务之前设置子线程的MDC数据。
     * <p/>
     * 在记录日志的时候，一般情况下我们会使用MDC来存储每个线程的特有参数，如身份信息等，以便更好的查询日志。
     * 但是Logback在最新的版本中因为性能问题，不会自动的将MDC的内存传给子线程。所以Logback建议在执行异步线程前
     * 先通过MDC.getCopyOfContextMap()方法将MDC内存获取出来，再传给线程。
     * 并在子线程的执行的最开始调用MDC.setContextMap(context)方法将父线程的MDC内容传给子线程。
     * <p>
     * https://logback.qos.ch/manual/mdc.html
     */
    public class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

        /**
         * 所有线程都会委托给这个execute方法，在这个方法中我们把父线程的MDC内容赋值给子线程
         * https://logback.qos.ch/manual/mdc.html#managedThreads
         *
         * @param runnable
         */
        @Override
        public void execute(Runnable runnable) {
            // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
            Map<String, String> context = MDC.getCopyOfContextMap();
            super.execute(() -> run(runnable, context));
        }

        /**
         * 子线程委托的执行方法
         *
         * @param runnable {@link Runnable}
         * @param context  父线程MDC内容
         */
        private void run(Runnable runnable, Map<String, String> context) {
            // 将父线程的MDC内容传给子线程
            if (context != null) {
                MDC.setContextMap(context);
            }
            try {
                // 执行异步操作
                runnable.run();
            } finally {
                // 清空MDC内容
                MDC.clear();
            }
        }
    }
}

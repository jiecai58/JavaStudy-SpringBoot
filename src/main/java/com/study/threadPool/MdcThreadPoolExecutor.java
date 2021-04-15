package com.study.threadPool;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A SLF4J MDC-compatible {@link ThreadPoolExecutor}.
 * <p/>
 * In general, MDC is used to store diagnostic information (e.g. a user's session id) in per-thread variables, to facilitate
 * logging. However, although MDC data is passed to thread children, this doesn't work when threads are reused in a
 * thread pool. This is a drop-in replacement for {@link ThreadPoolExecutor} sets MDC data before each task appropriately.
 * <p/>
 *
 * @author caijie
 * @since 2021/3/13 14:43
 */
public class MdcThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * 线程池保持ALIVE状态线程数
     */
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() >> 0b1;

    /**
     * 线程池最大线程数
     */
    private static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() << 0b1;

    /**
     * 空闲线程回收时间
     */
    private static final int KEEP_ALIVE_TIME = 0b1 << 0b1010;

    /**
     * 线程池等待队列
     */
    private static final int BLOCKING_QUEUE_SIZE = 0b1 << 0b1010;

    private final boolean useFixedContext;

    private final Map<String, String> fixedContext;

    public static ThreadPoolExecutor ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, Long keepAliveTime) {
        return ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, null, null, null, null);
    }

    /**
     * Thread pool creation, according to the hardware limit the number of threads
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   超出核心线程外的线程存活周期
     * @param workQueue       工具对象
     * @param threadFactory   线程工厂
     * @param handler         拒绝策略
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, Long keepAliveTime, TimeUnit unit,
                                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                                        RejectedExecutionHandler handler) {

        corePoolSize = Math.min(corePoolSize, CORE_POOL_SIZE);

        maximumPoolSize = Math.min(maximumPoolSize, MAX_POOL_SIZE);
        keepAliveTime = Math.max(KEEP_ALIVE_TIME, keepAliveTime);

        if (workQueue == null) {
            workQueue = new LinkedBlockingDeque<Runnable>(BLOCKING_QUEUE_SIZE);
        }

        if (threadFactory == null) {
            threadFactory = Executors.defaultThreadFactory();
        }

        if (handler == null) {
            handler = new CallerRunsPolicy();
        }
        if (unit == null) {
            unit = TimeUnit.MILLISECONDS;
        }
        return new MdcThreadPoolExecutor(MDC.getCopyOfContextMap(), corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
    }


    /**
     * Pool where task threads take fixed MDC from the thread that creates the pool.
     */
    @SuppressWarnings("unchecked")
    public static MdcThreadPoolExecutor newWithCurrentMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                          TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return new MdcThreadPoolExecutor(MDC.getCopyOfContextMap(), corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
    }

    /**
     * Pool where task threads always have a specified, fixed MDC.
     */
    public static MdcThreadPoolExecutor newWithFixedMdc(Map<String, String> fixedContext, int corePoolSize,
                                                        int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                                        RejectedExecutionHandler handler) {
        return new MdcThreadPoolExecutor(fixedContext, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private MdcThreadPoolExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize,
                                  long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }

    private MdcThreadPoolExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize,
                                  long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getContextForTask() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    /**
     * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
     * all delegate to this.
     */
    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, getContextForTask()));
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            Map previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }
}

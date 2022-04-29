package com.study.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *  最近项目中创建线程较多，有时候会存在线程死掉的情况，所以写了一个监听线程，监听线程的存活状态
 */
public class ThreadMonitor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ThreadMonitor.class);

    /**volatile关键字防止指令重排*/
    private volatile static ThreadMonitor monitor = null;

    /**线程安全*/
    private ConcurrentLinkedQueue<Thread> threadList = new ConcurrentLinkedQueue<>();

    private ThreadMonitor() {
    }

    /**
     * 单例模式，全局只产生一个对象
     */
    public static ThreadMonitor getInstance() {
        if (monitor == null) {
            synchronized (ThreadMonitor.class) {
                if (monitor == null) {
                    monitor = new ThreadMonitor();
                }
            }
        }
        return monitor;
    }


    public void addThreadMonitor(Thread thread) {
        threadList.add(thread);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ThreadMonitor");
        while (true) {
            try {
                Thread.sleep(2  * 1000);
            } catch (InterruptedException e) {
                log.error("sleep error ", e);
            }
            monitorAndRestart();
        }
    }

    private int monitorAndRestart() {
        int aliveThreadNum = 0;
        StringBuffer threadName = new StringBuffer();
        if (threadList != null && threadList.size() > 0) {
            for (Thread thread : threadList) {
                if (!thread.isAlive()) {
                    log.warn(thread.getName() + " is dead %>_<% , now try to restart");
                    thread.start();
                } else {
                    aliveThreadNum++;
                    threadName.append(thread.getName() + "存活！");
                }
            }
        }
        log.info("有[" + aliveThreadNum + "个]线程活着..." + threadName);
        return aliveThreadNum;
    }
}
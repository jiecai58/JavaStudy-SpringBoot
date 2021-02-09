package com.study.thread;

import java.util.concurrent.Semaphore;

/**
 * desc 二个线程交叉打印
 */
public class TwoThreadPrintWithWaitNotify {

    static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        //Executors.newFixedThreadPool(5);
        //Executors.newCachedThreadPool()
        //new ThreadPoolExecutor(1,4,)

        Object ob = new Object();
        new Thread(() -> {
            synchronized (ob) {
                while (true) {
                    try {
                        //Thread.currentThread().wait();
                        System.out.println("thread1 正在打印");
                        Thread.sleep(1000);
                        ob.notify();//释放当前线程持有的对象锁，并且唤醒等待该对象锁的线程继续运行
                        ob.wait();//释放当前线程持有的对象锁，进入阻塞状态(等待唤醒)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Thread.currentThread().notify();
            }
        }).start();


        new Thread(() -> {
            synchronized (ob) {
                while (true) {
                    try {
                        System.out.println("thread2 正在打印");
                        Thread.sleep(1000);
                        ob.notify();
                        ob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Thread.currentThread().notify();
            }
        }).start();
    }


}

package com.study.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * desc 二个线程交叉打印 使用LockSupport.park/unpark 控制
 */
public class TwoThreadPrintWithLockSupport {

    private static Thread thread1;
    private static Thread thread2;

    public static void main(String[] args) {

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        try {
                            //Thread.currentThread().wait();
                            System.out.println(Thread.currentThread().getName() + "正在打印");
                            Thread.sleep(1000);
                            LockSupport.unpark(thread2);
                            LockSupport.park();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "thread1");


         thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        LockSupport.park();
                        System.out.println(Thread.currentThread().getName() + " 正在打印");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LockSupport.unpark(thread1);
                }
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }


}

package com.study.threadPool;

import java.util.concurrent.Executors;

public class Thread {
    public static void main(String[] args) {
        Executors.newFixedThreadPool(1);
         //new ThreadPoolExecutor().submit()

/*        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool();
        Executors.newSingleThreadExecutor()*/
    }
}

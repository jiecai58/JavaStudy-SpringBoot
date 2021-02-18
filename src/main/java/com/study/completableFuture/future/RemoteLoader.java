package com.study.completableFuture.future;

import java.util.Map;

public interface RemoteLoader {
    Map<String, String> load();

    Map<String, String> load1(Integer p);

    default void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}





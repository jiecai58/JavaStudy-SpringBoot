package com.study.future;

import java.util.Map;

public interface RemoteLoader {
    Map<String, String> load();

    default void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}





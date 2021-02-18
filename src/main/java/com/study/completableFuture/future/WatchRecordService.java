package com.study.completableFuture.future;

import java.util.HashMap;
import java.util.Map;

public class WatchRecordService implements RemoteLoader {
    @Override
    public Map<String, String> load() {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("WatchRecordService","观看记录");
        return k;
    }

    @Override
    public Map<String, String> load1(Integer p) {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("WatchRecordService","观看记录");
        return k;
    }
}

package com.study.future;

import java.util.HashMap;
import java.util.Map;

public class CustomerInfoService implements RemoteLoader {
    @Override
    public Map<String, String> load() {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("CustomerInfo","基本信息");
        return k;
    }

    @Override
    public Map<String, String> load1(Integer p) {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("CustomerInfo","基本信息"+p);
        return k;
    }
}

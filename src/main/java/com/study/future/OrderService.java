package com.study.future;

import java.util.HashMap;
import java.util.Map;

public class OrderService implements RemoteLoader {
    @Override
    public Map<String, String> load() {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("OrderService","订单信息");
        return k;
    }
}

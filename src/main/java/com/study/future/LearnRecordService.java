package com.study.future;

import java.util.HashMap;
import java.util.Map;

public class LearnRecordService implements RemoteLoader {
    @Override
    public Map<String, String> load() {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("LearnRecord","学习信息");
        return k;
    }

    @Override
    public Map<String, String> load1(Integer p) {
        this.delay();
        HashMap<String, String> k = new HashMap<>();
        k.put("LearnRecord","学习信息");
        return k;
    }
}

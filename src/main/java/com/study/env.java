package com.study;

import java.util.Map;
import java.util.Set;

public class env {
    public Map SystemEnv(){
        Map<String,String> map = System.getenv();
        Set<Map.Entry<String,String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return map;
    }

}

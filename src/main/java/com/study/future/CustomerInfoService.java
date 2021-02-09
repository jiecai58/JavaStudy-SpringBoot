package com.study.future;

public class CustomerInfoService implements RemoteLoader {
    @Override
    public String load() {
        this.delay();
        return "基本信息";
    }
}

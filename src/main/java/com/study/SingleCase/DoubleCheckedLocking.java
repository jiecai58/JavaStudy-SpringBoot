package com.study.SingleCase;

public class DoubleCheckedLocking {

    private volatile static DoubleCheckedLocking singleton;
    private DoubleCheckedLocking (){}

    public static DoubleCheckedLocking getSingleton() {
        if (singleton == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (singleton == null) {
                    singleton = new DoubleCheckedLocking();
                }
            }
        }
        return singleton;
    }
}

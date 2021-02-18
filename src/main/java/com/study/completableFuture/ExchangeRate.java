package com.study.completableFuture;

public class ExchangeRate {
    public static void delay() {
        try {
            Thread.sleep(1000l);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static double getRate(String source,String target){
        delay();
        return 10;
    }

}

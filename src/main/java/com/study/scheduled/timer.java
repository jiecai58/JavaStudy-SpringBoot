package com.study.scheduled;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class timer {
    private static ScheduledThreadPoolExecutor  scheduled;
    public static void main(String[] args) {
        scheduled = new ScheduledThreadPoolExecutor(2);
    scheduled.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            System.out.println("test");
        }
    },0,40, TimeUnit.MICROSECONDS);
    }
    private void stopTimer(){
        if(scheduled != null){
            scheduled.shutdown();
        }
    }
}

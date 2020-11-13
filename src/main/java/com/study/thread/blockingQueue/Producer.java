package com.study.thread.blockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    private BlockingQueue<Integer> productFactory;
    public  Producer (BlockingQueue<Integer> productFactory){
       this.productFactory = productFactory;
    }
    public void run(){
        int i = 0;
        while (true) {
            try {
                productFactory.put(i);
                System.out.println("线程(" + Thread.currentThread().getName() + ")生产了一件产品:" + i + ";当前剩余商品" + productFactory.size() + "个");
                try {
                    Thread.sleep(new Random().nextInt(1000)+500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}

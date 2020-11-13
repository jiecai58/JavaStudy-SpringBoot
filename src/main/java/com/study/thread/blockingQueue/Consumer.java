package com.study.thread.blockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

    private BlockingQueue<Integer> productFactory;

    public Consumer(BlockingQueue<Integer> productFactory){
        this.productFactory = productFactory;
    }

    public void run() {
        consumer();
    }
    private void consumer() {
        while (true) {
            try {
                System.out.println("线程(" + Thread.currentThread().getName() + ")消费了一件产品:" + productFactory.take() + ";当前剩余商品" + productFactory.size() + "个");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(new Random().nextInt(1000)+500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

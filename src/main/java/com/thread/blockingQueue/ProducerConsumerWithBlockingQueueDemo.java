package com.thread.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerWithBlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> productFactory = new ArrayBlockingQueue(10);
        new Producer(productFactory).start();
        new Consumer(productFactory).start();
    }
}

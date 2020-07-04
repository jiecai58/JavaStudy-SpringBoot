package com.study.SingleCase;

import java.util.concurrent.*;

public class TestSingleCase2 {
    public static void main(String[] args) throws InterruptedException, ExceptionInInitializerError, ExecutionException {
        Callable<SingleCase2> c = new Callable<SingleCase2>() {
            @Override
            public SingleCase2 call() throws Exception {
                return SingleCase2.getInstance();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool( 2 );
        Future<SingleCase2> submit1 = executorService.submit( c );
        Future<SingleCase2> submit2 = executorService.submit( c );
        SingleCase2 singleCase1 = submit1.get();
        SingleCase2 singleCase2 = submit2.get();
        System.out.println( singleCase1==singleCase2 );
        System.out.println( singleCase1 );
        System.out.println( singleCase2 );
        executorService.shutdown();
    }

}

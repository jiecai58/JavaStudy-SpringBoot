package com.study.SingleCase;

import java.util.concurrent.*;

public class TestSingleCase {
    public static void main(String[] args) throws InterruptedException, ExceptionInInitializerError, ExecutionException {
        Callable<SingleCase> c = new Callable<SingleCase>() {
            @Override
            public SingleCase call() throws Exception {
                return SingleCase.getInstance();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool( 2 );
        Future<SingleCase> submit1 = executorService.submit( c );
        Future<SingleCase> submit2 = executorService.submit( c );
        SingleCase singleCase1 = submit1.get();
        SingleCase singleCase2 = submit2.get();
        System.out.println( singleCase1==singleCase2 );
        System.out.println( singleCase1 );
        System.out.println( singleCase2 );
        executorService.shutdown();
    }
}

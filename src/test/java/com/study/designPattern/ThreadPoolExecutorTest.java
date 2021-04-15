package com.study.designPattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.MDC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class ThreadPoolExecutorTest {

    private final static String SESSION_KEY = "sessionId";

    @Test
    public void xx() throws ExecutionException, InterruptedException {
        String token = UUID.randomUUID().toString().replace("-", "");
        MDC.put(SESSION_KEY, token);

        //ThreadPoolExecutor executorService = MdcThreadPoolExecutor.ThreadPoolExecutor(1, 5, 10L);

        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue workQueue =  new LinkedBlockingQueue<>();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 5, unit, workQueue);
        //submit
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                System.out.println("submit方法执行任务完成" + "   thread name: " + Thread.currentThread().getName());
                MDC.get(SESSION_KEY);
                return "SUCCESS";
            }
        });
        System.out.println(future.get());
        //submit lambda表达式
        Future<String> a = executorService.submit(()->{
            System.out.println("   thread namea: " + Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("submita方法执行任务完成" + "   thread name: " + Thread.currentThread().getName());
            return "a";
        });
        //execute
        executorService.execute(()->{
            System.out.println("   thread nameb: " + Thread.currentThread().getName());
            System.out.println("execute方法执行任务完成" + "   thread name: " + Thread.currentThread().getName());
        });
        
    }
}

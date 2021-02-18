package com.study.designPattern;

import com.google.common.collect.Lists;
import com.study.completableFuture.future.CustomerInfoService;
import com.study.completableFuture.future.LabelService;
import com.study.completableFuture.future.LearnRecordService;
import com.study.completableFuture.future.OrderService;
import com.study.completableFuture.future.RemoteLoader;
import com.study.completableFuture.future.WatchRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Future {

    //Future实现的版本
    @Test
    public void testFuture() {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<RemoteLoader> remoteLoaders = Arrays.asList(new CustomerInfoService(), new LearnRecordService());
        List<java.util.concurrent.Future<Map<String, String>>> futures = remoteLoaders.stream()
                .map(remoteLoader -> executorService.submit(remoteLoader::load))
                .collect(toList());
        List<Map<String, String>> customerDetail = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull).collect(toList());
        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }

    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                doSomething();
                future.complete("Finish");
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }).start();
        System.out.println(future.get());
    }

    private void doSomething() {
        System.out.println("doSomething...");
        throw new RuntimeException("Test Exception");
    }

    @Test
    public void testCompletableFuture2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            doSomething();
            return "Finish";
        });
        System.out.println(future.get());
    }


    @Test
    public void testCompletableFuture3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    doSomething();
                    return "Finish";
                })
                .exceptionally(throwable -> "Throwable exception message:" + throwable.getMessage());
        System.out.println(future.get());
    }

    //使用CompletableFuture来完成我们查询用户详情的API接口
    @Test
    public void testCompletableFuture31() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<RemoteLoader> remoteLoaders = Arrays.asList(
                new CustomerInfoService(),
                new LearnRecordService(),
                new LabelService(),
                new OrderService(),
                new WatchRecordService());
        List<CompletableFuture<Map<String, String>>> completableFutures = remoteLoaders
                .stream()
                .map(loader -> CompletableFuture.supplyAsync(loader::load))
                .collect(toList());

        List<Map<String, String>> customerDetail = completableFutures
                .stream()
                .map(CompletableFuture::join).collect(toList());

        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }

    /**
     * 自定义线程池，优化CompletableFuture
     * 使用并行流无法自定义线程池，但是CompletableFuture可以
     * 并行流和异步调用的性能不分伯仲,究其原因都一样,它们内部采用的是同样的通用线程池,默认都使用固定数目的线程,具体线程数取决于Runtime.getRuntime.availableProcessors()放回值,然而,.CompletableFuture具有一定的优势,因为它允许你对执行器进行配置,尤其是线程池的大小,让它以适合应用需求的方式进行配置,满足程序的要求,而这是并行流API无法提供的
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableFuture4() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<RemoteLoader> remoteLoaders = Arrays.asList(
                new CustomerInfoService(),
                new LearnRecordService(),
                new LabelService(),
                new OrderService(),
                new WatchRecordService());
        Integer k = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(remoteLoaders.size(), 50));

        List<CompletableFuture<Map<String, String>>> completableFutures = remoteLoaders
                .stream()
                .map(loader -> CompletableFuture.supplyAsync(loader::load
                        , executorService))
                .collect(toList());

        List<CompletableFuture<Map<String, String>>> completableFutures1 = remoteLoaders
                .stream()
                .map(loader -> CompletableFuture.supplyAsync(() -> loader.load1(k)
                        , executorService))
                .collect(toList());

        List<Map<String, String>> customerDetail = completableFutures1
                .stream()
                .map(CompletableFuture::join)
                .collect(toList());

        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }

    //Java8并行流
    @Test
    public void testParallelStream2() {
        long start = System.currentTimeMillis();
        List<RemoteLoader> remoteLoaders = Arrays.asList(
                new CustomerInfoService(),
                new LearnRecordService(),
                new LabelService(),
                new OrderService(),
                new WatchRecordService());
        List<Map<String, String>> customerDetail = remoteLoaders.parallelStream().map(RemoteLoader::load).collect(toList());
        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }


    //同步方式实现版本
    @Test
    public void testSync() {
        long start = System.currentTimeMillis();
        List<RemoteLoader> remoteLoaders = Arrays.asList(new CustomerInfoService(), new LearnRecordService());
        List<Map<String, String>> customerDetail = remoteLoaders.stream().map(RemoteLoader::load).collect(toList());
        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }


    /**
     * 并行流和CompletableFuture两者该如何选择
     * 这两者如何选择主要看任务类型，建议
     * <p>
     * 如果你的任务是计算密集型的，并且没有I/O操作的话，那么推荐你选择Stream的并行流，实现简单并行效率也是最高的
     * <p>
     * 如果你的任务是有频繁的I/O或者网络连接等操作，那么推荐使用CompletableFuture，采用自定义线程池的方式，根据服务器的情况设置线程池的大小，尽可能的让CPU忙碌起来
     * <p>
     * CompletableFuture的其他常用方法
     * thenApply、thenApplyAsync: 假如任务执行完成后，还需要后续的操作，比如返回结果的解析等等；可以通过这两个方法来完成
     * <p>
     * thenCompose、thenComposeAsync: 允许你对两个异步操作进行流水线的操作，当第一个操作完成后，将其结果传入到第二个操作中
     * <p>
     * thenCombine、thenCombineAsync：允许你把两个异步的操作整合；比如把第一个和第二个操作返回的结果做字符串的连接操作
     */
//使用thenCombine() thenCombineAsync()之后future1、future2之间是并行执行的，最后再将结果汇总。这一点跟thenCompose()不同。
    public void thenCombine() throws Exception {
        CompletableFuture<Double> result = CompletableFuture.supplyAsync(() -> "100")
                .thenCombine(CompletableFuture.supplyAsync(() -> 100),
                        (s, i) -> Double.parseDouble(s + i)
                );
        System.out.println(result.get());
    }

    //thenAcceptBoth接受两个线程的返回结果，并作出处理,没有返回
    public void thenAcceptBoth() throws Exception {
        CompletableFuture.supplyAsync(() -> "100")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> "100"),
                        (t, u) -> System.out.println("f1=" + t + ";f2=" + u + ";")
                );
    }

    public void testCompletableFutureThenApplyAccept() {
        CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .thenAccept((i) -> notifyByEmail()).join();
    }

    public void testCompletableFutureApplyAsync() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        CompletableFuture<Double> completableFuture =
                CompletableFuture
                        .supplyAsync(this::findAccountNumber, newFixedThreadPool) // 从线程池 newFixedThreadPool 获取线程执行任务
                        .thenApplyAsync(this::calculateBalance, newSingleThreadScheduledExecutor)
                        .thenApplyAsync(this::notifyBalance);
        Double balance = completableFuture.join();
    }

    private void notifyByEmail() {
        // business code
        System.out.println("send notify by email ...");
    }

    private Double notifyBalance(Double d) {
        // business code
        System.out.println(String.format("your balance is $%s", d));
        return 9527D;
    }

    private Double calculateBalance(Object o) {
        // business code
        return 9527D;
    }

    private Double findAccountNumber() {
        // business code
        return 9527D;
    }

    /**
     * 希望并行运行多个任务，并在所有任务完成后再进行一些处理。假设我们要查找 3 个不同用户的姓名并将结果合并。
     * 此时就可以使用 CompletableFuture 的静态方法 allOf，该方法会等待所有任务完成，需要注意的是该方法它不会返回所有任务的合并结果，因此我们必须手动组合任务的执行结果。
     */
    @Test
    public void testCompletableFutureAllof() {
        List<CompletableFuture<String>> list = Lists.newArrayListWithCapacity(4);
        IntStream.range(0, 3).forEach(num -> list.add(findName(num)));

        CompletableFuture<Void> allFuture = CompletableFuture
                .allOf(list.toArray(new CompletableFuture[0]));

        CompletableFuture<List<String>> allFutureList = allFuture
                .thenApply(val -> list.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        CompletableFuture<String> futureHavingAllValues = allFutureList
                .thenApply(fn -> String.join("", fn));

        String result = futureHavingAllValues.join();
    }

    private CompletableFuture<String> findName(int num) {
        return CompletableFuture.supplyAsync(() -> {
            // business code
            return "mghio" + num;
        });
    }


    //applyToEither哪个线程先返回就使用谁的返回结果进入该方法
    public void applyToEither() throws Exception {
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> "100")
                .applyToEither(CompletableFuture.supplyAsync(() -> "101"),
                        t -> {
                            System.out.println(t);
                            return t;
                        });
        System.out.println(result.get());
    }

    //acceptEither 和上面的方法作用相似，但是该方法是消费式，不会线程不会返回执行结果。
    public void acceptEither() throws Exception {
        CompletableFuture<Void> acceptEither = CompletableFuture.supplyAsync(() -> "100")
                .acceptEither(CompletableFuture.supplyAsync(() -> "101"),
                        t -> {
                            System.out.println(t);
                        });
    }

    //runAfterEither该方法非阻塞。只有在该方法执行之前有线程返回该方法才会执行。
    public void runAfterEither() throws Exception {
        CompletableFuture<Void> acceptEither = CompletableFuture.supplyAsync(() -> "100")
                .runAfterEither(CompletableFuture.supplyAsync(() -> "101"),
                        () -> System.out.println("上面有一个已经完成了。"));
    }

    //runAfterBoth同样为非阻塞方法，且只有当两个线程都在该方法执行之前结束。才会执行该方法。
    public void runAfterBoth() throws Exception {
        CompletableFuture<Void> acceptEither = CompletableFuture.supplyAsync(() -> "100")
                .runAfterBoth(CompletableFuture.supplyAsync(() -> "101"),
                        () -> System.out.println("上面两个任务都执行完成了。"));
    }

    //thenCompose将两个线程串行连接起来，只有第一个线程返回结果时，才会将返回值作为参数传给第二个线程执行
    public void thenCompose() throws Exception {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(3);
            System.out.println("t1=" + t);
            return t;
        }).thenCompose(param -> {
            return CompletableFuture.supplyAsync(() -> {
                int t = param * 2;
                System.out.println("t2=" + t);
                return t;
            });

        });
        System.out.println("thenCompose result : " + f.get());
    }

    //allOf同步等待所有异步线程任务结束
    public Object getStudentInfoWithCompletableFuture() {
        Map<String, Object> resultMap = new HashMap<>(10);
        try {
            CompletableFuture<Object> completableFutureStudentName = CompletableFuture.supplyAsync(() -> "caijie");

            CompletableFuture<Object> completableFutureSutdentAge = CompletableFuture.supplyAsync(() -> 18);


            CompletableFuture.allOf(completableFutureStudentName, completableFutureSutdentAge).join();

            resultMap.put("studentName", completableFutureStudentName.get());
            resultMap.put("studentAge", completableFutureSutdentAge.get());
        } catch (Exception e) {
            resultMap.put("errMsg", e.getMessage());
        }
        return resultMap;

    }

    public void allOfOrAnyOf() {
        CompletableFuture<Void> future = CompletableFuture
                .allOf(CompletableFuture.completedFuture("A"),
                        CompletableFuture.completedFuture("B"));
         //全部任务都需要执行完
        future.join();
        CompletableFuture<Object> future2 = CompletableFuture
                .anyOf(CompletableFuture.completedFuture("C"),
                        CompletableFuture.completedFuture("D"));
        //其中一个任务行完即可
        future2.join();
    }


//异常处理

    /**
     * 使用 exceptionally 方法处理异常，如果前面的方法失败并发生异常，则会调用异常回调
     */
    public void testCompletableFutureExceptionally() {
        CompletableFuture<Double> thenApply = CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .exceptionally(ex -> {
                    System.out.println("Exception " + ex.getMessage());
                    return 0D;
                });
        Double join = thenApply.join();
    }

    /**
     * handle 方法处理异常，使用该方式处理异常比上面的 exceptionally 方式更为灵活，
     * 我们可以同时获取到异常对象和当前的处理结果。
     */
    public void testCompletableFutureHandle() {
        CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .handle((ok, ex) -> {
                    System.out.println("最终要运行的代码...");
                    if (ok != null) {
                        System.out.println("No Exception !!");
                    } else {
                        System.out.println("Exception " + ex.getMessage());
                        return -1D;
                    }
                    return ok;
                });
    }

    /**
     * whenComplete 方法处理异常
     */
    public void testCompletableFutureWhenComplete() {
        CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .whenComplete((result, ex) -> {
                    System.out.println("result = " + result + ", ex = " + ex);
                    System.out.println("最终要运行的代码...");
                });
    }


}

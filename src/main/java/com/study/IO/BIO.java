package com.study.IO;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BIO {
    public static void  main(String[] args){
        //ExecutorService executorService = Executors.newFixedThreadPool(10);
        //ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            //ThreadPoolExecutor()
            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

            ServerSocket serverSocket = new ServerSocket(666);
            System.out.println("服务器启动动");
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("连见到一个客户端");
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        handler(socket);
                    }
                });
            }
        }catch (Exception e){

        }

    }
    public static void handler(Socket socket){
        try {
            System.out.println("线程信息 id ="+ Thread.currentThread().getId() +" 名字"+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true){
                System.out.println("线程信息 id ="+ Thread.currentThread().getId());
                int read = inputStream.read(bytes);
                if(read !=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭和Client连接");
            try{
                socket.close();

            }catch (Exception e){
                e.printStackTrace();
            }
       }
    }
}

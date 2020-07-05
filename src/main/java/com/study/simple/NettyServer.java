package com.study.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author leo
 */
public class NettyServer {
    public static void main(String[] args)throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            //设置两个线程组
            bootstrap.group(bossGroup,workGroup)
                    //使用NioSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    .option(ChannelOption.SO_BACKLOG,128)
                    //设置保持活动的连接状态,连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    //创建一个匿名通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception{
                            System.out.println("client socketchannel hashcode="+ ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给workGroup的EventLoop对应的管道设置处理器
            //绑定一个端口并同步生成一个ChannelFuture对象
            ChannelFuture future = bootstrap.bind(5878).sync();
            //给future 注册监听器(listener)，监控事件
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("listener port 5878 ,is success ");
                    }  else {
                        System.out.println("listener port 5878,is failure ");

                    }
                }
            });
            System.out.println("netty Server is ready ...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}


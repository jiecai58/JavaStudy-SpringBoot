package com.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道内加入处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个netty提供httpServerCodec,HttpServerCodec是netty提供的处理http的编码解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //自定义handler
        pipeline.addLast("myHttpServerHandler",new HttpServerHandler());
    }
}

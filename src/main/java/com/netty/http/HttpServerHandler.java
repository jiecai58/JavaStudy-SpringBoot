package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

   //读取客户端数据

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        //判断msg 是不是httprequest请求
        System.out.println("msg type="+ msg.getClass());
        if ( msg instanceof HttpRequest) {
            System.out.println("client address"+ channelHandlerContext.channel().remoteAddress());
            //回复信息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,i am server", CharsetUtil.UTF_8);
            //构造一个http的应答
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            //构建response返回
            channelHandlerContext.writeAndFlush(response);
        }
    }

}

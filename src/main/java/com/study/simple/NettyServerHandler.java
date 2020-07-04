package com.study.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * ChannelHandlerContext ctx 上下文对象，含有pipeline,通道channle,地址
     * Object msg 客户端发送的数据 默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void  channelRead(ChannelHandlerContext ctx, Object msg)throws Exception{
        System.out.println("server threads " + Thread.currentThread().getName());
        System.out.println("server ctx="+ ctx);
        System.out.println("看看channel和pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //双向链表，出入站
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("client set msg:"+ buf.toString(CharsetUtil.UTF_8));
        System.out.println("client address:"+ ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, client!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

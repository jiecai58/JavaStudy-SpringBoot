package com.study.thread.Disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Disruptor是英国外汇交易公司LMAX开发的一个高性能队列，研发的初衷是解决内存队列的延迟问题。与Kafka、RabbitMQ用于服务间的消息队列不同，
 * disruptor一般用于线程间消息的传递。基于Disruptor开发的系统单线程能支撑每秒600万订单。
 */

public class Consumer implements WorkHandler<PCData> {
    @Override
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getId() + " :Event: --" + event.getValue() * event.getValue() + "--");
    }
}

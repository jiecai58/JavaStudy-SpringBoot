package com.study.listenerEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    /** 注入ApplicationContext用来发布事件 */
    private final ApplicationContext applicationContext;

    /**
     * 下单
     *
     * @param orderId 订单ID
     */
    public String buyOrder(String orderId) {
        long start = System.currentTimeMillis();
        // 1.查询订单详情

        // 2.检验订单价格 （同步处理）
        applicationContext.publishEvent(new OrderProductEvent(this, orderId));

        // 3.短信通知（异步处理）
        applicationContext.publishEvent(new MsgEvent(orderId));

        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时：({})毫秒", end - start);
        return "购买成功";
    }

    @Test
    public void buyOrderTest() {
        orderService.buyOrder("732171109");
    }
}

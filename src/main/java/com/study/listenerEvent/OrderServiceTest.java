package com.study.listenerEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Strive
 * @date 2022/4/24 09:28
 * @description
 */
@SpringBootTest
public class OrderServiceTest {
    @Autowired private OrderService orderService;

    @Test
    public void buyOrderTest() {
        orderService.buyOrder("732171109");
    }
}


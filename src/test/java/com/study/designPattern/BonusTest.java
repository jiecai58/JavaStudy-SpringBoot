package com.study.designPattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BonusTest {

    @Test
    public void BonusPlan() {
        Integer bonus = 3;
        // 在工厂里通过奖金拿到对应的使用策略类
        AbstractBonusService strategyService = BonusStrategyFactory.getByBonus(bonus);
        // 调用策略类相应的方法
        System.out.println(strategyService.useBonusPlan(bonus));
    }
}

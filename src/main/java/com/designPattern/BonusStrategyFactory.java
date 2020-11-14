package com.designPattern;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BonusStrategyFactory {
    // 用map来保存如何使用奖金的策略类
    private static Map<Integer, AbstractBonusService> strategyMap = new ConcurrentHashMap<Integer, AbstractBonusService>();

    // 通过奖金多少查找对应的使用策略
    public static AbstractBonusService getByBonus(Integer bonus){
        return strategyMap.get(bonus);
    }

    // 将奖金和对应的使用策略注册到map里
    public static void register(Integer bonus, AbstractBonusService bonusService){
        Assert.notNull(bonus,"bonus can't be null");
        strategyMap.put(bonus, bonusService);
    }
}

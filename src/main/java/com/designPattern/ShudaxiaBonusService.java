package com.designPattern;

import org.springframework.stereotype.Component;

@Component
public class ShudaxiaBonusService extends AbstractBonusService {

    @Override
    public String useBonusPlan(Integer bonus) {
            return "去蜀大侠吃" + bonus + "元的火锅";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BonusStrategyFactory.register(9,this);
    }
}

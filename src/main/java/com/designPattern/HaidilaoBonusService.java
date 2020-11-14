package com.designPattern;

import org.springframework.stereotype.Component;

@Component
public class HaidilaoBonusService extends AbstractBonusService {

    @Override
    public String useBonusPlan(Integer bonus) {
        return "去海底捞吃" + bonus + "元的火锅";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BonusStrategyFactory.register(5,this);
    }
}

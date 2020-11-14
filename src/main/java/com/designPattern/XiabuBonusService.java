package com.designPattern;

import org.springframework.stereotype.Component;

@Component
public class XiabuBonusService extends AbstractBonusService {

    @Override
    public String useBonusPlan(Integer bonus) {
            return "去呷哺吃" + bonus + "元的火锅";
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        BonusStrategyFactory.register(3,this);
    }

}

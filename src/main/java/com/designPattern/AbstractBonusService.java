package com.designPattern;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractBonusService implements InitializingBean {
    public String useBonusPlan (Integer bonus){
        throw new UnsupportedOperationException();
    }
    public String useBonusPlan2 (Integer bonus){
        throw new UnsupportedOperationException();
    }
}

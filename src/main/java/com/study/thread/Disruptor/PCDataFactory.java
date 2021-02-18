package com.study.thread.Disruptor;

import com.lmax.disruptor.EventFactory;


public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}

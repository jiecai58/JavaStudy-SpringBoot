package com.study.testable.basic.model.mock;

abstract public class Box {

    protected String data;

    abstract public void put(String something);

    public String get() {
        return data;
    }

}

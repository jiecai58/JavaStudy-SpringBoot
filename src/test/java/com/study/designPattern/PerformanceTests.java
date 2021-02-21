package com.study.designPattern;

import junit.extensions.RepeatedTest;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Decorator 装饰器    两个测试类循环多次比较耗时
 */
public class PerformanceTests extends TestSetup {
    private long start;
    private int repeat;

    public PerformanceTests(Test test,int repeat) {
        super(new RepeatedTest(test, repeat));
        this.repeat = repeat;
    }
    protected void setUp()throws Exception{
        start = System.currentTimeMillis();
    }

    protected void tearDown() throws Exception{
        long duration = System.currentTimeMillis() - start;
        System.out.printf("%s repeated %, d time, takes $,d ms \n", getTest(), repeat, duration);
    }
    
    public static Test Suite() {
        TestSuite suite = new TestSuite();

        Test future = new TestSuite(com.study.designPattern.Future.class);
        Test bonusTest = new TestSuite(BonusTest.class);
        suite.addTest(new PerformanceTests(future, 1000));
        suite.addTest(new PerformanceTests(bonusTest,1000));
        return suite;
    }
}

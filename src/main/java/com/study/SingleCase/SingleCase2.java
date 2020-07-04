package com.study.SingleCase;

/**
 * 在内部类被加载和初始化时，才创建INSTANCE实例化
 * 静态内部类不会自动随着外部类的加载和初始化而初始化，它是要单独去加载和初始化。
 * 因为是在内部类加载和初始化时，创建的，所以是线程安全的。
 */
public class SingleCase2 {
    private SingleCase2(){

    }
    private static  class  Inner{
        private static final SingleCase2 INSTANCE = new SingleCase2();
    }
    public static SingleCase2 getInstance(){

        return Inner.INSTANCE;
    }

}

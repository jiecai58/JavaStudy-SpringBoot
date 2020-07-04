package com.study.SingleCase;

/**
 * 饿汉式：
 * 直接创建实例对象，不管是否需要这个对象都会去创建
 * 1.构造器私有化
 * 2.自行创建，并且用静态变量保存
 * 3.向外提供这个实例
 * 4.强化这个一个单例，我们可以用final，不允许修改
 */
public enum  SingeCase3 {
    INSTANCE
}

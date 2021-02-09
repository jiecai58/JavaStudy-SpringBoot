package com.study.aspect;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{
    @Override
    public  void  a(){
        System.out.println("进入A方法");
        this.b();
    }
    @Override
    @Info()
    public void b(){
        System.out.println("进入B方法");
    }
}

package com.study.SingleCase;

public class SingleCase {
    private static SingleCase instance;
    private SingleCase(){

    }
    public static  SingleCase getInstance(){
        if(instance == null){
            synchronized (SingleCase.class){
                if(instance == null){
                    try{
                        Thread.sleep( 1000 );
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    instance = new SingleCase();
                }
            }
        }
        return instance;
    }

}

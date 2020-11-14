package com.GC;

public class Demo1 {
    public static void main(String[] args) {
        byte[] array1 = new byte[2* 1024 * 1024];
        array1 = new byte[2* 1024 * 1024];
        array1 = new byte[2* 1024 * 1024];
        array1 = null;
        byte[] array2 = new byte[128 * 1024];
        byte[] array3 = new byte[2*1024 * 1024];
    }
}
//jdk1.8 堆内存10M,年轻代5M,老年代5M,eden:S1:S2=8:1:1,大于10M直接放入老年代
//-XX:InitialHeapSize=10M -XX:MaxHeapSize=10M -XX:NewSize=5M -XX:MaxNewSize=5M  -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
//-XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:NewSize=10485760 -XX:MaxNewSize=10485760  -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
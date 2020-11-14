package com.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateIndex {

    @Test
    //创建索引
    public void test1(){
        // 创建Client连接对象
        /*Settings settings = Settings.builder().put("cluster.name", "my‐elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings) .addTransportAddress(newInetSocketTransportAddress(InetAddress.getByName("192.168.179.128"),9300));
        //创建名称为blog2的索引
        client.admin().indices().prepareCreate("blog2").get();
        //释放资源
        client.close();*/

    }
}

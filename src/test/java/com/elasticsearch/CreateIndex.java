package com.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateIndex {

    private String IP;
    private int PORT;

    /*ES 端口9200与9300的区别：
    9200作为Http协议，主要用于外部通讯
    9300作为Tcp协议，jar之间就是通过tcp协议通讯
    ES集群之间是通过9300进行通讯*/
    @Before
    public void init(){
        this.IP = "192.168.179.128";
        this.PORT = 9200;
    }

    @Test
    //创建索引
    public void test1() throws UnknownHostException {
        // 创建Client连接对象
        Settings settings = Settings.builder().put("cluster.name", "my‐elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings) .addTransportAddress(new TransportAddress(InetAddress.getByName(this.IP),this.PORT));
        //创建名称为blog2的索引
        client.admin().indices().prepareCreate("blog2").get();
        //释放资源
        client.close();

    }
}

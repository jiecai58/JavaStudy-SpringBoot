package com.elasticsearch;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateMapping {
    @Test
    //创建映射
    public void test1() throws Exception{
        /// 创建Client连接对象
        Settings settings = Settings.builder().put("cluster.name", "my‐elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.179.128"),9300));
        // 添加映射
        /**
         * 格式：
         * "mappings" : {
         "article" : {
         "dynamic" : "false",
         "properties" : {
         "id" : { "type" : "string" },
         "content" : { "type" : "string" },
         "author" : { "type" : "string" }
         }
         }
         }*/
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")
                .startObject("id")
                .field("type", "integer")
                .field("store", "yes")
                .endObject()
                        .startObject("title")
                        .field("type", "string")
                        .field("store", "yes")
                        .field("analyzer", "ik_smart")
                        .endObject()
                        .startObject("content")
                        .field("type", "string")
                        .field("store", "yes")
                        .field("analyzer", "ik_smart")
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject();
        // 创建映射
        PutMappingRequest mapping = Requests.putMappingRequest("blog2")
                .type("article").source(builder);
        client.admin().indices().putMapping(mapping).get();
        //释放资源
        client.close();

    }
}

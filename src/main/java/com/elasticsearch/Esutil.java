package com.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.similarity.ScriptedSimilarity;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Esutil {
    public static Client client = null;

    /**
     * 获取客户端
     * @return
     */
    public static  Client getClient() {
        if(client != null){
            return client;
        }
        Settings settings = Settings.builder().put("cluster.name", "my‐elasticsearch").build();

        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("node1"), 9300));
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("node2"), 9300))
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("node3"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    public static String addIndex(String index, String type, ScriptedSimilarity.Doc Doc){
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", 1);
        hashMap.put("title", "title");
        hashMap.put("describe", "describe");
        hashMap.put("author", "author");

        IndexResponse response = getClient().prepareIndex(index, type).setSource(hashMap).execute().actionGet();
        return response.getId();
    }


    public static Map<String, Object> search(String key, String index, String type, int start, int row){
        SearchRequestBuilder builder = getClient().prepareSearch(index);
        builder.setTypes(type);
        builder.setFrom(start);
        builder.setSize(row);

        Map<String, Object> map = new HashMap<String,Object>();
        return map;
    }

//	public static void main(String[] args) {
//		Map<String, Object> search = Esutil.search("hbase", "tfjt", "doc", 0, 10);
//		List<Map<String, Object>> list = (List<Map<String, Object>>) search.get("dataList");
//	}
}

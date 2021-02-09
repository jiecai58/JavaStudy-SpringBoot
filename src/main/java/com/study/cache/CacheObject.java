package com.study.cache;

import lombok.Data;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Data
@CacheConfig(cacheNames = "texts")
@Component // spring cache生效必须初始化为一个bean
public class CacheObject {

    @Cacheable(value = "texts")
    public String query() {
        System.out.println("调用方法内部，未走缓存");
        return "text_query";
    }

    @CachePut(value = "texts")
    public String update() {
        return "text_updated";
    }

    @CacheEvict(value = "texts")
    public String delete() {
        return "text_deleted";
    }

}
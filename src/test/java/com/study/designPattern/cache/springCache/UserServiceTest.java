package com.study.designPattern.cache.springCache;

import com.study.cache.springcache.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private CacheManager cacheManager;

    @Test
    public void testCache() {
        /*Long id = 1L;
        User user = new User(id, "ye", "swiftleaf612@gmail.com");
        userService.save(user);
        Assert.assertNotNull(cacheManager.getCache(String.valueOf(id)));
        cacheManager.getCache("user");
        userService.findById(id);*/
    }
}

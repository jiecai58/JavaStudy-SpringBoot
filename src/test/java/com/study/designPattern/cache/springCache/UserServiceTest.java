package com.study.designPattern.cache.springCache;

import com.study.cache.springcache.entity.User;
import com.study.cache.springcache.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
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

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#user.id");
        /*String result = (String) expression.getValue();
        System.out.println("result:" + result);*/

        Long id = 1L;
        User user = new User(id, "ye", "swiftleaf612@gmail.com");
        userService.save(user);
        System.out.printf(userService.get(new User(id, "ye", null)).getEmail());
        Assert.assertNotNull(cacheManager.getCache(String.valueOf(id)));
        userService.findById(id);
    }
}

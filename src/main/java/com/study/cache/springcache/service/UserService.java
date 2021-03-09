package com.study.cache.springcache.service;

import com.study.cache.springcache.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@CacheConfig(cacheNames = "user")
public class UserService{

    Set<User> users = new HashSet<User>();


    @CachePut(value = "mycache", key = "#user.name + #user.email",condition = "#user.age < 35")
    public User save1(User user) {
        users.add(user);
        return user;
    }

    @Cacheable(value = "mycache", key = "#name.concat(#email)",condition = "#name eq 'wangd'")
    //@MyCache(value = "#name.concat(#email)",condition = "#name eq 'ye'")
   public User findByNameAndEmail(String name, String email){
       return new User(null,name,0,email);
   }

    @CachePut(key = "#user.id")
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Cacheable(key = "#user.id")
    public User get(User user) {
        return user;
    }


    @CachePut(key = "#user.id")
    public User update(User user) {
        users.remove(user);
        users.add(user);
        return user;
    }

    @CacheEvict(key = "#user.id")
    public User delete(User user) {
        users.remove(user);
        return user;
    }

    @CacheEvict(allEntries = true)
    //@CacheEvict(value = "texts")
    public void deleteAll() {
        users.clear();
    }

    @Cacheable(key = "#id")
    public User findById(final Long id) {
        System.out.println("cache miss, invoke find by id, id:" + id);
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

}
package com.jackson0714.passjava.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.common.utils.Query;

import com.jackson0714.passjava.question.dao.TypeDao;
import com.jackson0714.passjava.question.entity.TypeEntity;
import com.jackson0714.passjava.question.service.ITypeService;

import static java.lang.Thread.sleep;


@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements ITypeService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TypeEntity> page = this.page(
                new Query<TypeEntity>().getPage(params),
                new QueryWrapper<TypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TypeEntity> getTypeEntityList() {
        // 1.初始化 redis 组件
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // 2.从缓存中查询数据
        String typeEntityListCache = ops.get("typeEntityList");
        // 3.如果缓存中没有数据
        if (StringUtils.isEmpty(typeEntityListCache)) {
            System.out.println("The cache is empty");
            // 4.从数据库中查询数据
            List<TypeEntity> typeEntityListFromDb = this.list();
            // 5.将从数据库中查询出的数据序列化 JSON 字符串
            typeEntityListCache = JSON.toJSONString(typeEntityListFromDb);
            // 6.将序列化后的数据存入缓存中
            ops.set("typeEntityList", typeEntityListCache);
            return typeEntityListFromDb;
        }
        // 7.如果缓存中有数据，则从缓存中拿出来，并反序列化为实例对象
        List<TypeEntity> typeEntityList = JSON.parseObject(typeEntityListCache, new TypeReference<List<TypeEntity>>(){});
        return typeEntityList;
    }

    @Override
    public List<TypeEntity> getTypeEntityListByLock() {
        // 1.从缓存中查询数据
        String typeEntityListCache = redisTemplate.opsForValue().get("typeEntityList");
        if (!StringUtils.isEmpty(typeEntityListCache)) {
            // 2.如果缓存中有数据，则从缓存中拿出来，并反序列化为实例对象，并返回结果
            List<TypeEntity> typeEntityList = JSON.parseObject(typeEntityListCache, new TypeReference<List<TypeEntity>>(){});
            return typeEntityList;
        }
        // 3. 加锁
        synchronized (this) {
            return getDataFromDB();
        }
    }

    private List<TypeEntity> getDataFromDB() {
        String typeEntityListCache;
        // 4.从缓存中查询数据
        typeEntityListCache = redisTemplate.opsForValue().get("typeEntityList");
        if (!StringUtils.isEmpty(typeEntityListCache)) {
            // 5.如果缓存中有数据，则从缓存中拿出来，并反序列化为实例对象，并返回结果
            List<TypeEntity> typeEntityList = JSON.parseObject(typeEntityListCache, new TypeReference<List<TypeEntity>>(){});
            return typeEntityList;
        }
        // 6.如果缓存中没有数据，从数据库中查询数据
        System.out.println("The cache is empty");
        List<TypeEntity> typeEntityListFromDb = this.list();
        // 7.将从数据库中查询出的数据序列化 JSON 字符串
        typeEntityListCache = JSON.toJSONString(typeEntityListFromDb);
        // 8.将序列化后的数据存入缓存中
        redisTemplate.opsForValue().set("typeEntityList", typeEntityListCache, 1, TimeUnit.DAYS);
        return typeEntityListFromDb;
    }

    @Override
    public List<TypeEntity> getTypeEntityListByRedisDistributedLock() throws InterruptedException {
        // 1.先抢占锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "123");
        if(lock) {
            // 2.抢占成功，执行业务
            List<TypeEntity> typeEntityListFromDb = getDataFromDB();
            // 3.解锁
            redisTemplate.delete("lock");
            return typeEntityListFromDb;
        } else {
            // 4.休眠一段时间
            //sleep(100);
            // 5.抢占失败，等待锁释放
            return getTypeEntityListByRedisDistributedLock();
        }
    }

}
package com.jackson0714.passjava.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackson0714.passjava.common.utils.PageUtils;
import com.jackson0714.passjava.common.utils.Query;

import com.jackson0714.passjava.question.dao.TypeDao;
import com.jackson0714.passjava.question.entity.TypeEntity;
import com.jackson0714.passjava.question.service.ITypeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

import static java.lang.Thread.sleep;


@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements ITypeService {

    @Autowired
    RedissonClient redisson;

    @Resource
    @Qualifier("stringRedisTemplate")
    private StringRedisTemplate myStringRedisTemplate;

    @Resource
    @Qualifier("stringRedisTemplateTransaction")
    private StringRedisTemplate stringRedisTemplateTransaction;

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
        ValueOperations<String, String> ops = myStringRedisTemplate.opsForValue();
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
        String typeEntityListCache = myStringRedisTemplate.opsForValue().get("typeEntityList");
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
        typeEntityListCache = myStringRedisTemplate.opsForValue().get("typeEntityList");
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
        myStringRedisTemplate.opsForValue().set("typeEntityList", typeEntityListCache, 1, TimeUnit.DAYS);
        return typeEntityListFromDb;
    }

    /**
     * 分布式锁钻石方案
     * @return list
     * @throws InterruptedException
     */
    @Override
    public List<TypeEntity> getTypeEntityListByRedisDistributedLock() throws InterruptedException {
        // 1.生成唯一 id
        String uuid = UUID.randomUUID().toString();
        // 2. 抢占锁
        Boolean lock = myStringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
        if(lock) {
            System.out.println("抢占成功：" + uuid);
            // 3.抢占成功，执行业务
            List<TypeEntity> typeEntityListFromDb = getDataFromDB();

            // 4.Lua 脚本解锁
            String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            myStringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);

            System.out.println("解锁成功：" + uuid);

            return typeEntityListFromDb;
        } else {
            System.out.println("抢占失败，等待锁释放");
            // 4.休眠一段时间
            sleep(100);
            // 5.抢占失败，等待锁释放
            return getTypeEntityListByRedisDistributedLock();
        }
    }

    /**
     * 分布式锁王者方案
     * @return list
     */
    @Override
    public List<TypeEntity> getTypeEntityListByRedissonDistributedLock() {
        // 1.设置分布式锁
        RLock lock = redisson.getLock("lock");
        // 2.占用锁
        lock.lock();
        System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
        List<TypeEntity> typeEntityListFromDb = null;
        try {
            // 3.获取数据
            typeEntityListFromDb = getDataFromDB();
            Thread.sleep(10000); // 模拟长时间执行任务
        } catch (Exception e) {
            System.out.println("异常");
            // TODO
        } finally {
            // 4.释放锁
            System.out.println("释放成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
            lock.unlock();
        }
        return typeEntityListFromDb;
    }


    /**
     * Redis 事务操作
     * 悟空聊架构
     */
    @Override
    public void testRedisMutil() {
        myStringRedisTemplate.setEnableTransactionSupport(true);
        myStringRedisTemplate.multi();
        myStringRedisTemplate.opsForValue().set("passjava", "123");
        myStringRedisTemplate.opsForValue().set("悟空聊架构", "abc");
        myStringRedisTemplate.exec();
    }

    // TransactionSynchronizationManager.unbindResource(myStringRedisTemplate.getConnectionFactory());

    /**
     * 自增
     * 悟空聊架构
     * @return 自增后的值
     */
    @Override
    @Transactional
    public Long testTransactionAnnotations() {
        // Redis 递增
        return myStringRedisTemplate.opsForValue().increment("count", 1);
    }

    @Override
    public void enableTransactionSupport(Boolean flag) {
        myStringRedisTemplate.setEnableTransactionSupport(flag);
    }

    /**
     * 不添加 Spring 事务注解 @Transactional
     * 悟空聊架构
     * @return 自增后的值
     */
    @Override
    public Long testWithOutTransactionAnnotations() {
        // Redis 递增
        return myStringRedisTemplate.opsForValue().increment("count", 1);
    }

}
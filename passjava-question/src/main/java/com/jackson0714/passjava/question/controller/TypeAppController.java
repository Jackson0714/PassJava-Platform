package com.jackson0714.passjava.question.controller;

import com.jackson0714.passjava.common.utils.R;
import com.jackson0714.passjava.question.entity.TypeEntity;
import com.jackson0714.passjava.question.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 题目-题目类型表
 *
 * @author：公众号：悟空聊架构
 * @website：www.passjava.cn
 * @date 2020-04-25 22:34:04
 */
@RestController
@RequestMapping("question/v1/app/type")
public class TypeAppController {

    private Map<String, Object> cache = new HashMap<>();

    @Autowired
    private ITypeService ITypeService;

    /**
     * 查询题目类型列表 by hashmap
     *
     * @author：公众号：悟空聊架构
     * @website：www.passjava.cn
     */
    @RequestMapping("/list")
    public R list(){
        List<TypeEntity> typeEntityListCache = (List<TypeEntity>) cache.get("typeEntityList");
        // 如果缓存中没有数据
        if (typeEntityListCache == null) {
            System.out.println("The cache is empty");
            // 从数据库中查询数据
            List<TypeEntity> typeEntityList = ITypeService.list();
            // 将数据放入缓存中
            typeEntityListCache = typeEntityList;
            cache.put("typeEntityList", typeEntityListCache);
        }
        return R.ok().put("typeEntityList", typeEntityListCache);
    }

    /**
     * 查询题目类型列表 by redis
     *
     * @author：公众号：悟空聊架构
     * @website：www.passjava.cn
     */
    @RequestMapping("/list-by-redis")
    public R listByRedis(){

        List<TypeEntity> typeEntityList = ITypeService.getTypeEntityList();
        return R.ok().put("typeEntityList", typeEntityList);
    }

    /**
     * 查询题目类型列表 by redis 加锁
     *
     * @author：公众号：悟空聊架构
     * @website：www.passjava.cn
     */
    @RequestMapping("/list-by-redis-lock")
    public R listByRedisLock(){

        List<TypeEntity> typeEntityList = ITypeService.getTypeEntityListByLock();
        return R.ok().put("typeEntityList", typeEntityList);
    }

    /**
     * 查询题目类型列表 by redis 分布式锁（钻石方案）
     *
     * @author：公众号：悟空聊架构
     * @website：www.passjava.cn
     */
    @RequestMapping("/list-by-redis-distributed-lock")
    public R listByRedisDistributedLock() throws InterruptedException {

        List<TypeEntity> typeEntityList = ITypeService.getTypeEntityListByRedisDistributedLock();
        return R.ok().put("typeEntityList", typeEntityList);
    }

    /**
     * 查询题目类型列表 by redisson 分布式锁（王者方案）
     *
     * @author：公众号：悟空聊架构
     * @website：www.passjava.cn
     */
    @RequestMapping("/list-by-redisson-distributed-lock")
    public R listByRedissonDistributedLock() throws InterruptedException {

        List<TypeEntity> typeEntityList = ITypeService.getTypeEntityListByRedissonDistributedLock();
        return R.ok().put("typeEntityList", typeEntityList);
    }
}

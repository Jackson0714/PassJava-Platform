package com.jackson0714.passjava.question.controller;

import com.alibaba.fastjson.JSON;
import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.common.utils.R;
import com.jackson0714.passjava.question.entity.TypeEntity;
import com.jackson0714.passjava.question.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 题目-题目类型表
 *
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
@RestController
@RequestMapping("question/v1/app/type")
public class TypeAppController {

    private Map<String, Object> cache = new HashMap<>();

    @Autowired
    private ITypeService ITypeService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 查询题目类型列表
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
     * 查询题目类型列表
     *
     * @author：公众号：悟空聊架构
     * @website：www.passjava.cn
     */
    @RequestMapping("/list-by-redis")
    public R listByRedis(){

        // 初始化 redis 组件
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        // 查询数据
        String typeEntityListCache = ops.get("typeEntityList");
        // 如果缓存中没有数据
        if (typeEntityListCache == null) {
            System.out.println("The cache is empty");
            // 从数据库中查询数据
            List<TypeEntity> typeEntityListFromDb = ITypeService.list();
            // 将数据放入缓存中
            String s = JSON.toJSONString(typeEntityListFromDb);
            ops.set("typeEntityList", s);
        }
        List<TypeEntity> typeEntityList = (List<TypeEntity>) JSON.parseObject(typeEntityListCache);
        return R.ok().put("typeEntityList", typeEntityList);
    }
}

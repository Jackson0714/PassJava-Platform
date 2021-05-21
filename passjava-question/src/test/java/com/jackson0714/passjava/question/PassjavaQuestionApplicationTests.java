package com.jackson0714.passjava.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackson0714.passjava.question.entity.TypeEntity;
import com.jackson0714.passjava.question.service.ITypeService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class PassjavaQuestionApplicationTests {

    @Autowired
    ITypeService ITypeService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redisson;

    @Test
    public void TestRedisson() {
        System.out.println(redisson);
    }

    @Test
    public void TestStringRedisTemplate() {
        // 初始化 redis 组件
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 存储数据
        ops.set("悟空", "悟空聊架构_" + UUID.randomUUID().toString());
        // 查询数据
        String wukong = ops.get("悟空");
        System.out.println(wukong);
    }

    // 创建题目类型
    @Test
    void testCreateType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setType("javaBasic");
        ITypeService.save(typeEntity);
        System.out.println("创建成功");
    }

    // 更新题目类型
    @Test
    void testUpdateType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setType("jvm");
        ITypeService.updateById(typeEntity);
        System.out.println("修改成功");
    }

    // 查询题目类型
    @Test
    void testSelectType() {
        List<TypeEntity> typeEntityList = ITypeService.list(new QueryWrapper<TypeEntity>().eq("id",1L));
        typeEntityList.forEach((item)-> {
            System.out.println(item);
        });
        System.out.println("查询成功");
    }

    // 删除题目类型记录
    @Test
    void testRemoveType() {
        ITypeService.removeById(1L);
        System.out.println("删除成功");
    }
}

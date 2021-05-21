package com.jackson0714.passjava.question.controller;

import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 题目-测试 Redisson
 *
 * @author：公众号：悟空聊架构
 * @website：www.passjava.cn
 * @date 2020-04-25 22:34:04
 */
@RestController
@RequestMapping("question/v1/redisson/test")
public class RedissonTestController {

    @Autowired
    RedissonClient redisson;

    @ResponseBody
    @GetMapping("test-lock")
    public String testLock() {


        // 1.获取锁，只要锁的名字一样，获取到的锁就是同一把锁。
        RLock lock = redisson.getLock("WuKong-lock");

        // 2.加锁
        lock.lock(8, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
            Thread.sleep(10000);
        } catch (Exception e) {
            //TODO
        } finally {
            lock.unlock();
            // 3.解锁
            System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
        }

        return "test lock ok";
    }

    /**
     * 停车，占用停车位
     * 总共 3 个车位
     */
    @ResponseBody
    @RequestMapping("park")
    public String park() throws InterruptedException {
        // 获取信号量（停车场）
        RSemaphore park = redisson.getSemaphore("park");
        // 获取一个信号（停车位）
        park.acquire();

        return "OK";
    }

    /**
     * 释放车位
     * 总共 3 个车位
     */
    @ResponseBody
    @RequestMapping("leave")
    public String leave() throws InterruptedException {
        // 获取信号量（停车场）
        RSemaphore park = redisson.getSemaphore("park");
        // 释放一个信号（停车位）
        park.release();

        return "OK";
    }

}

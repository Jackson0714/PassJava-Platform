package com.jackson0714.passjava.question.service.impl;

import com.jackson0714.passjava.common.utils.SnowflakeUtilV2;
import com.jackson0714.passjava.common.utils.SnowflakeUtill;
import org.junit.Test;

/**
 * @author 悟空聊架构
 * @description TODO
 * @date 2022/11/14
 * @site www.passjava.cn
 * @github https://github.com/Jackson0714
 */
public class TestSnowflake {
    private SnowflakeUtill snowFlakeUtill = new SnowflakeUtill();

    @Test
    public void testSnowflake() {
       Long id = snowFlakeUtill.getNextId();
       System.out.println(id);
    }

    @Test
    public void testSnowflakeV2() {
        SnowflakeUtilV2 snowflakeUtilV2 = new SnowflakeUtilV2();
        Long id = snowflakeUtilV2.nextId();
        System.out.println(id);
    }
}

package com.jackson0714.passjava.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.passjava.question.entity.TypeEntity;

import java.util.Map;

/**
 * 题目-题目类型表
 *
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


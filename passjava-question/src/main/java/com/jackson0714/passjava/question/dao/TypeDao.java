package com.jackson0714.passjava.question.dao;

import com.jackson0714.passjava.question.entity.TypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目-题目类型表
 * 
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
@Mapper
public interface TypeDao extends BaseMapper<TypeEntity> {
	
}

package com.jackson0714.passjava.study.dao;

import com.jackson0714.passjava.study.entity.StudyTimeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学习-用户学习时常表
 * 
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-15 17:50:49
 */
@Mapper
public interface StudyTimeDao extends BaseMapper<StudyTimeEntity> {
	
}

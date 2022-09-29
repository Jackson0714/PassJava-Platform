package com.jackson0714.passjava.study.dao;

import com.jackson0714.passjava.study.entity.ViewLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学习-用户学习浏览记录表
 * 
 * @author 公众号：悟空聊架构
 * @site www.passjava.cn
 * @date 2020-04-15 17:50:49
 */
@Mapper
public interface ViewLogDao extends BaseMapper<ViewLogEntity> {
	
}

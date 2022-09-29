package com.jackson0714.passjava.channel.dao;

import com.jackson0714.passjava.channel.entity.AccessTokenEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 渠道-认证表
 * 
 * @author 公众号：悟空聊架构
 * @site www.passjava.cn
 * @date 2020-04-15 15:08:21
 */
@Mapper
public interface AccessTokenDao extends BaseMapper<AccessTokenEntity> {
	
}

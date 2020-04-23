package com.jackson0714.passjava.content.dao;

import com.jackson0714.passjava.content.entity.NewsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容-资讯表
 * 
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-15 17:13:14
 */
@Mapper
public interface NewsDao extends BaseMapper<NewsEntity> {
	
}

package com.jackson0714.passjava.member.dao;

import com.jackson0714.passjava.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员-会员表
 * 
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-15 17:42:00
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}

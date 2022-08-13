package com.jackson0714.passjava.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackson0714.passjava.common.utils.PageUtils;
import com.jackson0714.passjava.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员-会员表
 *
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-15 17:42:00
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);


    String sendCoupon(int num) throws Exception;


    MemberEntity getMemberByUserId(String userId);
}


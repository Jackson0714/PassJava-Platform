package com.jackson0714.passjava.channel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackson0714.passjava.common.utils.PageUtils;
import com.jackson0714.passjava.common.utils.Query;

import com.jackson0714.passjava.channel.dao.AccessTokenDao;
import com.jackson0714.passjava.channel.entity.AccessTokenEntity;
import com.jackson0714.passjava.channel.service.AccessTokenService;


@Service("accessTokenService")
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenDao, AccessTokenEntity> implements AccessTokenService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccessTokenEntity> page = this.page(
                new Query<AccessTokenEntity>().getPage(params),
                new QueryWrapper<AccessTokenEntity>()
        );

        return new PageUtils(page);
    }

}
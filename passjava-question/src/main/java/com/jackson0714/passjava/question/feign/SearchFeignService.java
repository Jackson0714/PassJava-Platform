package com.jackson0714.passjava.question.feign;

import com.jackson0714.common.to.es.QuestionEsModel;
import com.jackson0714.common.utils.R;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/4/6 15:14
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
@Mapper
@FeignClient("passjava-search")
public interface SearchFeignService {

    @PostMapping("search/question/save")
    R saveQuestion(@RequestBody QuestionEsModel questionEsModel);
}

package com.jackson0714.passjava.search.service;

import com.jackson0714.common.to.es.QuestionEsModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/3/31 17:56
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
@Mapper
@Component
public interface IQuestionService {
    boolean save(QuestionEsModel questionEsModel) throws IOException;
}

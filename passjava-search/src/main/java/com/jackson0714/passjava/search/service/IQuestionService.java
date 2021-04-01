package com.jackson0714.passjava.search.service;

import com.jackson0714.common.to.es.QuestionEsModel;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/3/31 17:56
 * @Site: www.jayh.club
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
public interface IQuestionService {
    void save(QuestionEsModel questionEsModel);
}

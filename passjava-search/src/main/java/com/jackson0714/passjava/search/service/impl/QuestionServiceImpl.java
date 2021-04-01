package com.jackson0714.passjava.search.service.impl;

import com.jackson0714.common.to.es.QuestionEsModel;
import com.jackson0714.passjava.search.service.IQuestionService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/3/31 17:59
 * @Site: www.jayh.club
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public void save(QuestionEsModel questionEsModel) {

    }
}

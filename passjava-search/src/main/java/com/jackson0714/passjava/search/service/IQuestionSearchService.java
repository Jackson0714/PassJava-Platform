package com.jackson0714.passjava.search.service;

import com.jackson0714.passjava.search.vo.SearchParam;
import org.elasticsearch.action.search.SearchResponse;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/4/6 21:31
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
public interface IQuestionSearchService {

    SearchResponse search(SearchParam param);
}

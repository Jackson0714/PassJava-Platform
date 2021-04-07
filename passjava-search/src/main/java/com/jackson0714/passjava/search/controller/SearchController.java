package com.jackson0714.passjava.search.controller;

import com.jackson0714.passjava.search.service.IQuestionSearchService;
import com.jackson0714.passjava.search.vo.SearchParam;
import com.jackson0714.passjava.search.vo.SearchQuestionResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/4/6 21:26
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
@RequestMapping("/search")
@RestController
public class SearchController {

    @Autowired
    IQuestionSearchService questionSearchService;

    @PostMapping("/question/list")
    public SearchQuestionResponse list(SearchParam param) {

        return questionSearchService.search(param);

    }
}

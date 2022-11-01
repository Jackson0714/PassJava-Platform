package com.jackson0714.passjava.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackson0714.passjava.common.utils.R;
import com.jackson0714.passjava.question.entity.QuestionEntity;
import com.jackson0714.passjava.question.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


/**
 * 
 *
 * @author 公众号：悟空聊架构
 * @公众号：悟空聊架构
 * @date 2022-11-01 22:34:04
 */
@RestController
@RequestMapping("question/v1/app/question")
@Slf4j
public class QuestionAppController {
    @Autowired
    private IQuestionService IQuestionService;

    /**
     * 列表
     */
    @RequestMapping("/list/{type}")
    public R list(@PathVariable("type") String type){
        long time = System.currentTimeMillis();
        List<QuestionEntity> list = IQuestionService.list(type);
        System.out.println("耗时："+ (System.currentTimeMillis() - time));
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
		QuestionEntity question = IQuestionService.info(id);
        return R.ok().put("question", question);
    }
}

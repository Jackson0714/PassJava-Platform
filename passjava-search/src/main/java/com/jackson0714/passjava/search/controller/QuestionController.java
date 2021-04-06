package com.jackson0714.passjava.search.controller;

import com.jackson0714.common.exception.BizCodeEnum;
import com.jackson0714.common.to.es.QuestionEsModel;
import com.jackson0714.common.utils.R;
import com.jackson0714.passjava.search.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/3/31 17:49
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */

@RequestMapping("/search")
@RestController
public class QuestionController {

    @Autowired
    IQuestionService questionService;

    // 保存题目到 ES。
    @PostMapping("/question/save")
    public R saveQuestion(@RequestBody QuestionEsModel questionEsModel) {
        boolean result =false;
        try {
            result = questionService.save(questionEsModel);
        } catch (Exception e) {
            result =false;
        }

        if (!result) {
            return R.error(BizCodeEnum.QUESTION_SAVE_EXCEPTION.getCode(), BizCodeEnum.QUESTION_SAVE_EXCEPTION.getMsg());
        }

        return R.ok();
    }
}

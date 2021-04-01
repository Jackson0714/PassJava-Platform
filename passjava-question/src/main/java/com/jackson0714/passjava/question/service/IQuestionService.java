package com.jackson0714.passjava.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.passjava.question.entity.QuestionEntity;

import java.util.Map;

/**
 * 
 *
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
public interface IQuestionService extends IService<QuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean saveQuestion(QuestionEntity question);

    boolean updateQuestion(QuestionEntity question);
}


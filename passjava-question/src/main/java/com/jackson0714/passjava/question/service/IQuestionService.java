package com.jackson0714.passjava.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jackson0714.passjava.common.utils.PageUtils;
import com.jackson0714.passjava.question.entity.QuestionEntity;

import java.util.Map;

/**
 * 
 *
 * @author 公众号 | 悟空聊架构
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
public interface IQuestionService extends IService<QuestionEntity> {

    IPage<QuestionEntity> queryPage1(IPage<QuestionEntity> page, Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);

    QuestionEntity info(Long id);

    boolean saveQuestion(QuestionEntity question);

    boolean updateQuestion(QuestionEntity question);

    boolean createQuestion(QuestionEntity question);
}


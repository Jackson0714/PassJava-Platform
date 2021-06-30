package com.jackson0714.passjava.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.common.utils.R;
import com.jackson0714.passjava.question.entity.QuestionEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 
 *
 * @author 公众号 | 悟空聊架构
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
public interface IQuestionService extends IService<QuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    QuestionEntity info(Long id);

    boolean saveQuestion(QuestionEntity question);

    boolean updateQuestion(QuestionEntity question);

    QuestionEntity createQuestion(QuestionEntity question);
}


package com.jackson0714.passjava.search.vo;

import com.jackson0714.common.to.es.QuestionEsModel;
import lombok.Data;

import java.util.List;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/4/7 16:50
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
@Data
public class SearchQuestionResponse {
    private List<QuestionEsModel> questionList;
    private Integer pageNum;
    private Long total;
    private Integer totalPages;
}

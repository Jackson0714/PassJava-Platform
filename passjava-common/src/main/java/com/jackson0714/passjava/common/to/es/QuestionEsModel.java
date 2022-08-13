package com.jackson0714.passjava.common.to.es;

import lombok.Data;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/3/31 15:16
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */

/*
"properties": {
    "id": {
        "type": "long"
    }
    "title": {
        "type": "keyword",
        "analyzer": "ik_smart"
    },
    "answer": : {
        "type": "keyword",
        "analyzer": "ik_smart"
	},
	"typeName": {
        "type": "keyword",
    },
}
* */
@Data
public class QuestionEsModel {
    private Long id;
    private String title;
    private String answer;
    private String typeName;
}

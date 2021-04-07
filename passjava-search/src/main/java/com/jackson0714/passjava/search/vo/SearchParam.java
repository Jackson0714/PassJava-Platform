package com.jackson0714.passjava.search.vo;

import lombok.Data;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2021/4/6 21:28
 * @Site: www.passjava.cn
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
@Data
public class SearchParam {
    private String keyword; // 全文匹配的关键字
    private String id; // 题目 id
    private Integer pageNum; // 题目 id
}

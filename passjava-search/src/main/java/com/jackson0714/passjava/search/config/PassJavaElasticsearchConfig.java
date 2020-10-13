package com.jackson0714.passjava.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 公众号 | 悟空聊架构
 * @Date: 2020/10/8 17:02
 * @Site: www.passjava.club
 * @Github: https://github.com/Jackson0714/PassJava-Platform
 */
@Configuration
public class PassJavaElasticsearchConfig {

    @Bean
    // 给容器注册一个 RestHighLevelClient，用来操作 ES
    // 参考官方文档：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.9/java-rest-high-getting-started-initialization.html
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.56.10", 9200, "http")));
    }
}

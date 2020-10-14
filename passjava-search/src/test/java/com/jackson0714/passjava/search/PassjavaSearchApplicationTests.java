package com.jackson0714.passjava.search;

import com.alibaba.fastjson.JSON;
import com.jackson0714.passjava.search.config.PassJavaElasticsearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;

import java.io.IOException;

@SpringBootTest
class PassjavaSearchApplicationTests {

    @Qualifier("restHighLevelClient")
    @Autowired
    private RestHighLevelClient client;

    /**
     * 测试存储数据到 ES。
     * */
    @Test
    public void testIndexData() throws IOException {
        IndexRequest request = new IndexRequest("users");
        request.id("1"); // 文档的 id

        //构造 User 对象
        User user = new User();
        user.setUserName("PassJava");
        user.setAge("18");
        user.setGender("Man");

        //User 对象转为 JSON 数据
        String jsonString = JSON.toJSONString(user);

        // JSON 数据放入 request 中
        request.source(jsonString, XContentType.JSON);

        // 执行插入操作
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    @Data
    class User {
        private String userName;
        private String age;
        private String gender;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    @Test
    /**
     * 示例：搜索 address 中包含 big 的所有人的年龄分布 ( 前 10 条 ) 以及平均年龄，以及平均薪资
     *
     * GET bank/_search
     * {
     *   "query": {
     *     "match": {
     *       "address": "road"
     *  }
     *   },
     *   "aggs": {
     *     "ageAggr": {
     *       "terms": {
     *         "field": "age",
     *         "size": 10
     *        }
     *     },
     *     "ageAvg": {
     *       "avg": {
     *         "field": "age"
     *       }
     *     },
     *     "balanceAvg": {
     *       "avg": {
     *         "field": "balance"
     *       }
     *    }
     *  }
     * }
     * */
    public void testSearchData() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("bank");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 1.1）address 中包含 big 的所有人
        sourceBuilder.query(QueryBuilders.matchQuery("address","road"));
        // 1.2）按照年龄分布进行聚合
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        sourceBuilder.aggregation(ageAgg);
        // 1.3）、计算平均薪资
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        sourceBuilder.aggregation((balanceAvg));
        System.out.println("检索参数：" + sourceBuilder.toString());

        request.source(sourceBuilder);

        // 2、执行检索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 3、分析结果
        System.out.println(response.toString());
        // 3.1）、拿到命中结果
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        // 3.2）、遍历命中结果
//        for(SearchHits hits: searchHits) {
//            hit.getSourcesAsString();
//            Account account = JSON.parseObject(string, Account.class);
//        }


    }
}

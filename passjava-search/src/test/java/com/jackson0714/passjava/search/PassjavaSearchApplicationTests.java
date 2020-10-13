package com.jackson0714.passjava.search;

import com.alibaba.fastjson.JSON;
import com.jackson0714.passjava.search.config.PassJavaElasticsearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void contextLoads() {
        System.out.println(client);
    }
}

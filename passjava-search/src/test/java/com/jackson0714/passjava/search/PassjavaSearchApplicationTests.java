package com.jackson0714.passjava.search;

import com.alibaba.fastjson.JSON;
import com.jackson0714.passjava.search.config.PassJavaElasticsearchConfig;
import lombok.Data;
import lombok.ToString;
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
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
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

    @ToString
    @Data
    static class BankMember {
        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }
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
     * 示例：搜索 address 中包含 road 的所有人的年龄分布 ( 前 10 条 ) 以及平均年龄，平均薪资
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
        // 1.1）address 中包含 road 的所有人
        sourceBuilder.query(QueryBuilders.matchQuery("address","road"));
        // 1.2）按照年龄分布进行聚合
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        sourceBuilder.aggregation(ageAgg);
        // 1.3）计算平均薪资
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        sourceBuilder.aggregation((balanceAvg));
        System.out.println("检索参数：" + sourceBuilder.toString());

        request.source(sourceBuilder);

        // 2、执行检索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 3、分析结果
        System.out.println(response.toString());
        // 3.1）获取查到的数据。
        SearchHits hits = response.getHits();
        // 3.2）获取真正命中的结果
        SearchHit[] searchHits = hits.getHits();
        // 3.3）遍历命中结果
        for (SearchHit hit: searchHits) {
            String hitStr = hit.getSourceAsString();
            BankMember bankMember = JSON.parseObject(hitStr, BankMember.class);
            System.out.println(bankMember);
        }
        // 3.4）获取聚合信息
        // 参考文档：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search.html
        Aggregations aggregations = response.getAggregations();
        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("用户年龄： " + keyAsString + " 人数：" + bucket.getDocCount());
        }

        Avg balanceAvg1 = aggregations.get("balanceAvg");
        System.out.println("平均薪资：" + balanceAvg1.getValue());

        /*
        打印结果：
        用户年龄： 21 人数：4
        用户年龄： 26 人数：4
        用户年龄： 28 人数：4
        用户年龄： 32 人数：3
        用户年龄： 25 人数：2
        用户年龄： 36 人数：2
        用户年龄： 39 人数：2
        用户年龄： 23 人数：1
        用户年龄： 27 人数：1
        用户年龄： 29 人数：1
        平均薪资：28578.137931034482
         */
    }

    /*
    * {
	"took": 7,
	"timed_out": false,
	"_shards": {
		"total": 1,
		"successful": 1,
		"skipped": 0,
		"failed": 0
	},
	"hits": {
		"total": {
			"value": 29,
			"relation": "eq"
		},
		"max_score": 3.5234027,
		"hits": [{
			"_index": "bank",
			"_type": "account",
			"_id": "431",
			"_score": 3.5234027,
			"_source": {
				"account_number": 431,
				"balance": 13136,
				"firstname": "Laurie",
				"lastname": "Shaw",
				"age": 26,
				"gender": "F",
				"address": "263 Aviation Road",
				"employer": "Zillanet",
				"email": "laurieshaw@zillanet.com",
				"city": "Harmon",
				"state": "WV"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "436",
			"_score": 3.5234027,
			"_source": {
				"account_number": 436,
				"balance": 27585,
				"firstname": "Alexander",
				"lastname": "Sargent",
				"age": 23,
				"gender": "M",
				"address": "363 Albemarle Road",
				"employer": "Fangold",
				"email": "alexandersargent@fangold.com",
				"city": "Calpine",
				"state": "OR"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "532",
			"_score": 3.5234027,
			"_source": {
				"account_number": 532,
				"balance": 17207,
				"firstname": "Hardin",
				"lastname": "Kirk",
				"age": 26,
				"gender": "M",
				"address": "268 Canarsie Road",
				"employer": "Exposa",
				"email": "hardinkirk@exposa.com",
				"city": "Stouchsburg",
				"state": "IL"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "873",
			"_score": 3.5234027,
			"_source": {
				"account_number": 873,
				"balance": 43931,
				"firstname": "Tisha",
				"lastname": "Cotton",
				"age": 39,
				"gender": "F",
				"address": "432 Lincoln Road",
				"employer": "Buzzmaker",
				"email": "tishacotton@buzzmaker.com",
				"city": "Bluetown",
				"state": "GA"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "83",
			"_score": 3.5234027,
			"_source": {
				"account_number": 83,
				"balance": 35928,
				"firstname": "Mayo",
				"lastname": "Cleveland",
				"age": 28,
				"gender": "M",
				"address": "720 Brooklyn Road",
				"employer": "Indexia",
				"email": "mayocleveland@indexia.com",
				"city": "Roberts",
				"state": "ND"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "88",
			"_score": 3.5234027,
			"_source": {
				"account_number": 88,
				"balance": 26418,
				"firstname": "Adela",
				"lastname": "Tyler",
				"age": 21,
				"gender": "F",
				"address": "737 Clove Road",
				"employer": "Surelogic",
				"email": "adelatyler@surelogic.com",
				"city": "Boling",
				"state": "SD"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "146",
			"_score": 3.5234027,
			"_source": {
				"account_number": 146,
				"balance": 39078,
				"firstname": "Lang",
				"lastname": "Kaufman",
				"age": 32,
				"gender": "F",
				"address": "626 Beverley Road",
				"employer": "Rodeomad",
				"email": "langkaufman@rodeomad.com",
				"city": "Mahtowa",
				"state": "RI"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "331",
			"_score": 3.5234027,
			"_source": {
				"account_number": 331,
				"balance": 46004,
				"firstname": "Gibson",
				"lastname": "Potts",
				"age": 34,
				"gender": "F",
				"address": "994 Dahill Road",
				"employer": "Zensus",
				"email": "gibsonpotts@zensus.com",
				"city": "Frizzleburg",
				"state": "CO"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "355",
			"_score": 3.5234027,
			"_source": {
				"account_number": 355,
				"balance": 40961,
				"firstname": "Gregory",
				"lastname": "Delacruz",
				"age": 38,
				"gender": "M",
				"address": "876 Cortelyou Road",
				"employer": "Oulu",
				"email": "gregorydelacruz@oulu.com",
				"city": "Waterloo",
				"state": "WV"
			}
		}, {
			"_index": "bank",
			"_type": "account",
			"_id": "386",
			"_score": 3.5234027,
			"_source": {
				"account_number": 386,
				"balance": 42588,
				"firstname": "Wallace",
				"lastname": "Barr",
				"age": 39,
				"gender": "F",
				"address": "246 Beverly Road",
				"employer": "Concility",
				"email": "wallacebarr@concility.com",
				"city": "Durham",
				"state": "IN"
			}
		}]
	},
	"aggregations": {
		"lterms#ageAgg": {
			"doc_count_error_upper_bound": 0,
			"sum_other_doc_count": 5,
			"buckets": [{
				"key": 21,
				"doc_count": 4
			}, {
				"key": 26,
				"doc_count": 4
			}, {
				"key": 28,
				"doc_count": 4
			}, {
				"key": 32,
				"doc_count": 3
			}, {
				"key": 25,
				"doc_count": 2
			}, {
				"key": 36,
				"doc_count": 2
			}, {
				"key": 39,
				"doc_count": 2
			}, {
				"key": 23,
				"doc_count": 1
			}, {
				"key": 27,
				"doc_count": 1
			}, {
				"key": 29,
				"doc_count": 1
			}]
		},
		"avg#balanceAvg": {
			"value": 28578.137931034482
		}
	}
}
    * */
}

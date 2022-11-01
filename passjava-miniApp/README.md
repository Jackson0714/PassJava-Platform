## 版本区别
### passJavaCloud

passJavaCloud：腾讯云开发版小程序，不需要后台即可访问题库。
小程序的题库都在腾讯云开发的数据库中。可以自行添加题库。

### passJavaRest
passJavaRest：进阶版小程序。后端采用 Spring Cloud 微服务作为服务支撑，数据库也不是云数据库了，而是 mysql。Rest 版本虽然目前还不完善，但已经和后端打通。

本文主要内容如下：
![](https://img-blog.csdnimg.cn/img_convert/c1ef95d8cccc194b72a7688a0a32bfa9.png)

## 一、缘起

PassJava 开源项目是一个`面试刷题`的开源系统，后端采用 Spring Cloud 微服务可以用零碎时间利用`小程序`查看常见面试题，夯实Java 技术栈，当然题库不限于 Java，还有微服务。

之前有读者问我小程序开源么，因之前在写分布式算法系列的文章，所以开源项目中断了一段时间，现在继续更新开源项目。

为了让读者朋友们更好地学习该项目，我已经将小程序代码开源。

![](https://img-blog.csdnimg.cn/img_convert/5a55f92bf35abdb98ecfe528f307aac8.png)

小程序的代码地址在 github 上面，别忘记点个 star，素质三连哈～

> https://github.com/Jackson0714/PassJava-Platform/tree/master/passjava-miniApp

小程序体验码如下：

![](https://img-blog.csdnimg.cn/img_convert/baa49eb4c0231f3a5edc7bdc2be68159.png)

小程序界面：

![](https://img-blog.csdnimg.cn/img_convert/540a24d6907aeff93bc053385d1c4eb9.gif)

小程序有两个版本：云开发版和进阶版。下面分别进行介绍。

## 二、云开发版

### 2.1 小程序简介

小程序·云开发是微信团队联合腾讯云推出的专业的小程序开发服务。

开发者可以使用云开发快速开发小程序、小游戏、公众号网页等，并且原生打通微信开放能力。

开发者无需搭建服务器，可免鉴权直接使用平台提供的 API 进行业务开发。

#### 2.1.1 优势：

- 无需搭建服务器，只需使用平台提供的各项能力，即可快速开发业务。
- 无需管理证书、签名、秘钥，直接调用微信 API 。复用微信私有协议及链路，保证业务安全性。
- 支持环境共享，一个后端环境可开发多个小程序、公众号、网页等，便捷复用业务代码与数据。
- 开发者可以使用任意语言和框架进行代码开发，构建为容器后，快速将其托管至云开发。
- 支持按量计费模式，后端资源根据业务流量自动扩容，先使用后付费，无需支付闲置成本。

#### 2.1.2 特点

**云数据库**：文档型数据库，稳定可靠；支持在小程序端和云函数中调用。

**存储**：云端文件存储，自带 CDN 加速，支持在前端直接上传/下载，可在云开发控制台可视化管理。

**云函数**：在云端运行的代码，微信私有协议天然鉴权，开发者只需编写自身业务逻辑代码。

**云托管**：支持托管服务容器，不限框架和语言，常驻运行、天然鉴权，可快速进行业务迁移

#### 2.1.3 缺点

- 适合个人开发。
- 按照流量付费，访问人多了的话，需要购买流量。
- 因服务端是腾讯自带的，所以不能使用其他后端中间件来实现功能增强，比如 Redis，RabbitMQ。
- 数据库和存储也是腾讯自带的，所以不能使用其他数据库和存储，比如 mysql、阿里云。

### 2.2 小程序下载使用

#### 2.2.1 题库简介

题库功能如下图所示：

- 第一步：进入到选择题目类型。这里有 12 种类别，点击其中一个可跳转到题目列表页。

![题目类型](https://img-blog.csdnimg.cn/img_convert/bdeb3527ca89fe05f7505dc7c1411a21.png)

- 第二步：进入到题库列表页。下图是 Spring 题库列表页，可以点击查看进入到题目的详情页。

![Spring 题库](https://img-blog.csdnimg.cn/img_convert/ae9436f90577cf36f5944d9b14552e72.png)

- 第三步：进入到题目详情页。如下图所示就是 spring 面试题的详情页，加载的内容就是上传到云存储的 markdown 文件。后面会讲解如何上传题目。

![题目详情页](https://img-blog.csdnimg.cn/img_convert/f83181dc35b5b803568923ff2ff0acc5.png)

#### 2.2.3 上传题目

要用这套云开发版的小程序的话，需要先上传题目文件到云存储。

如下图所示，我上传了很多 markdown 文件，大家可以自行编写 markdown 文件，每一个文件对应一个题目，里面的内容是题目的问题+答案。

![image-20210408155451254](https://img-blog.csdnimg.cn/img_convert/49bd12de1c695dcc2adf030119bd804b.png)

#### 2.2.3 添加题目记录

然后需要在数据库中插入数据，来提供给小程序查询。

![](https://img-blog.csdnimg.cn/img_convert/5c4065288a393d200b7a3dc91098009c.png)

我导出了一条记录，大家可以直接插入到数据库中。

```json
{
	"_id": "27a98c6c-0477-4edb-8ef3-e35f0501b31c",
	"question": "1.JAVA 异常分类及处理？",
	"answer": "cloud://test-0jlva.7465-test-0jlva-1254012214/markdown/02_JavaBasic/一、JAVA 异常分类及处理.md",
	"number": "1",
	"subTitle": "每天进步一点点",
	"type": "javaBasic",
	"level": "1"
}
```

- question：题目的问题。
- answer：题目的答案。
- number：题目的序号。
- subTitle：题目的副标题。
- type：题目的类型，目前有 `javaBasic`、`jvm`、`spring` 等 12 种。也可以自行添加。

![共 12 种](https://img-blog.csdnimg.cn/img_convert/1258270a9f9bbcad347a4e65110ebbe3.png)

markdown 格式的题目已经上传到了 github，目前只有部分，欢迎大家 commit。

```http
https://github.com/Jackson0714/passJavaKnowledge/tree/master
```

另外我还有 1000 道面试题的 pdf 也可以用作题库，需要的同学在公众号后台回复`悟空`领取下。

### 2.3 小程序原理

小程序原理图如下：

![](http://cdn.jayh.club/uPic/image-20210411092400523.png)

原理图说明：

1）调用 自己编写的云函数 getJavaQuestionList 获取列表；

2）调用 自己编写的云函数 getJavaQuestionDetail 获取详情的 Markdown文件路径；

3）调用 系统自带的云函数 downloadFile 下载 Markdown文件保存为临时文件；

4）调用 小程序自带的 saveFileSync 将临时文件保存到本地；

5）调用 小程序自带的 readFileSync 将本地文件读入缓存（注意：开发者工具上不需要保存到本地也可以正常读取）；

6）使用 towxml 开源组件将缓存中markdown内容转成小程序可以识别的元素

7）给 图片元素添加预览事件

因小程序主要是用来刷题，附加的功能比如 banner 广告位、热点推荐等就不在这里介绍了。

对于有些同学来说，云开发还比较陌生，需要多看看官方文档熟悉下才能熟练进行二次开发。

## 三、进阶版

该项目的小程序进阶版指的是不用云开发，后端采用 Spring Cloud 微服务作为服务支撑，数据库也不是云数据库了，而是 mysql。Rest 版本虽然目前还不完善，但已经和后端打通。

### 3.1 使用条件

目前需要以下条件才能将前端和后端连起来一起用：

- 本地后台微服务启动成功。

  目前要求 passjava-gateway、passjava-question、RenrenAplication 启动成功。

![](https://img-blog.csdnimg.cn/img_convert/ec70fd21f373f2fd12d4add379c96df1.png)

- MySQL 数据库和表结构创建成功。

  数据库文件在 \data\sql 目录，需要都执行。执行成功后，业务表总共有 5 个业务模块， 7 张表，如下图所示：

![](https://img-blog.csdnimg.cn/img_convert/1e3cda87a301c17032766628c81aaecd.png)

- Nacos 服务启动成功且已配置好微服务的相关配置。

  如何配置可参考前面的文章。访问地址：http://192.168.56.1:8848/nacos。访问 Nacos，并登陆 Nacos 后，如下图所示：

![](https://img-blog.csdnimg.cn/img_convert/d7e843262d5a0b48504cf6e97890c76d.png)

- passjava-portal 管理控制台启动成功。

  通过 http://localhost:8001/ 访问。

![](https://img-blog.csdnimg.cn/img_convert/152288df8443882abc3742b28348da57.png)

- 题目管理功能正常使用。

  可以创建题目类型和创建题目。

![](https://img-blog.csdnimg.cn/img_convert/a78a5b4728d2726466e8685d5295a2cf.png)

- 打开小程序代码正常。

可以查询题目列表和查询题目的答案。

<video src="../../../Library/Containers/com.tencent.xinWeChat/Data/Library/Application%20Support/com.tencent.xinWeChat/2.0b4.0.9/481c5e6e089be08afdce2aaf8dd41856/Message/MessageTemp/864abc48c494dd2cb9b3490c29cc2d95/Video/864abc48c494dd2cb9b3490c29cc2d95_689009204208_v_1667316404215886.mp4"></video>

### 前端核心代码

#### 查询题目列表

代码路径：miniprogram/pages/javaQuestionList/javaQuestionList.js

![](http://cdn.jayh.club/uPic/image-20221101233245282L5ju0N.png)

#### 查询题目详情

代码路径：miniprogram/pages/javaQuestionDetail/javaQuestionDetail.js

![](http://cdn.jayh.club/uPic/image-20221101233315920rpLzZv.png)

### 后端核心代码

代码路径：

``` SH
src/main/java/com/jackson0714/passjava/question/controller/QuestionAppController.java
```

#### 查询题目列表的 API

![](http://cdn.jayh.club/uPic/image-20221101233539957ORXdSO.png)

#### 查询题目详情的 API

![](http://cdn.jayh.club/uPic/image-20221101233558397xb0sIN.png)

### 数据库

表：passjava_qms.qms_question

#### 题目列表数据

![image-20221101233819567](http://cdn.jayh.club/uPic/image-20221101233819567qgmgY8.png)

#### 题目类型数据

表：passjava_qms.qms_type

![](http://cdn.jayh.club/uPic/image-20221101233849267LTWzNH.png)



## 四、开源地址

我把后端、前端、小程序都上传到同一个仓库里面了，大家可以通过 github 或 码云访问。地址如下：

> **Github**: https://github.com/Jackson0714/PassJava-Platform
>
> **码云**：https://gitee.com/jayh2018/PassJava-Platform
>
> **配套教程**：www.passjava.cn

调试代码是个辛苦活，精力有限，在写技术文章时，还需要抽出时间倒腾这个开源项目，所以是否值得一赞？



参考资料：

https://developers.weixin.qq.com/miniprogram/dev/wxcloud/basis/getting-started.html
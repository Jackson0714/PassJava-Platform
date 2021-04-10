const banner = { 
  "item": [
    {
      'isShow': true,
      "bannerUrl": "https://7465-test-0jlva-1254012214.tcb.qcloud.la/banner/pmp2.png?sign=4498f01db8b7349915d34352d98f0c83&t=1556897293", 
      "navigationType": "pmp", 
      "needRegister": true, 
      "displayOrder": 1 
    }]
}

const dailyQuestions = {
  "items": [
    {
      'id': 123,
      "imageUrl": "https://7465-test-0jlva-1254012214.tcb.qcloud.la/banner/pmp2.png?sign=4498f01db8b7349915d34352d98f0c83&t=1556897293",
      "title": "2019-05-08 练习题",
      "subTitle": "每天进步一点点",
      "pagePath": '/dailyQuestions/daily0508.html'
    }, {
      'id': 234,
      "imageUrl": "https://7465-test-0jlva-1254012214.tcb.qcloud.la/banner/pmp2.png?sign=4498f01db8b7349915d34352d98f0c83&t=1556897293",
      "title": "2019-05-09 练习题",
      "subTitle": "每天进步一点点",
      "pagePath": '/dailyQuestions/daily0509.html'
    }]
}

const releaseTimeLine = {
  "name": "面试突击",
  "description": "这款小程序可以帮助大家利用零碎时间刷题和复习。纯个人开发，请多多支持！",
  "img": "https://7465-test-0jlva-1254012214.tcb.qcloud.la/profile/banner1.png",
  "website": "博客园：www.jackson0714.cnblogs.com",
  "github": "GitHub：www.github.com/Jackson0714",
  "wechatService": "公众号：悟空聊架构",
  "wechat": "微信号：PassJava",
  "aboutMe": "公众号：悟空聊架构，手写了 2W 字分布式算法总结和 6W 字 SpringCloud 实战总结，关注即可免费获取。",
  "timeLinelist": [
    {
      "date": "2019年3月08日",
      "version": "V1.3.0",
      "description": "热点推荐支持跳转到微信图文",
    },
    {
      "date": "2019年3月07日",
      "version": "V1.2.0",
      "description": "使用小程序云开发获取问题列表，问题详情；增加总结功能和热点推荐功能",
    },{
      "date": "2019年3月06日",
      "version": "V1.1.0",
      "description": "展示Java知识点列表，用Markdown展示知识点详情",
    }, {
      "date": "2020年3月05日",
      "version": "V1.0.0",
      "description": "小程序初始版本，首页功能展示"
    }
  ]
}

const mock = {
  banner: banner,
  dailyQuestions: dailyQuestions,
  releaseTimeLine: releaseTimeLine
}

module.exports = mock

/**
 * 小程序配置文件
 */
const env = require('env')
const baseUrl = env.baseUrl

const config = {
  appid: env.miniAppid,
  version: '2.2.1',
  qrCodeTime: 300000,
  service: {
  },
  pages: {
    'index': '/pages/index/index',//首页
    'profile': '/pages/profile/profile',//我的
    'bannerPage': '/pages/bannerPage/bannerPage',// banner
    'dailyQuestions': '/pages/dailyQuestions/dailyQuestions', // 每日5道题列表
    'dailyQuestionsWeb': '/pages/dailyQuestionsWeb/dailyQuestionsWeb', // 每日5道题 pmpDoc
    'webview': '/pages/webview/webview', // webview
    'exam': '/pages/exam/exam', //在线刷题
    'dailyExamList': '/pages/dailyExamList/dailyExamList', //每日练习
    'simulatedExamList': '/pages/simulatedExamList/simulatedExamList', // 模拟试题
    'developing': '/pages/developing/developing', //开发中
    'topic': '/pages/topic/topic', //专题秘籍
    'range': '/pages/range/range', //答题排行,
    'javaBasic': '/pages/javaBasic/javaBasic', //java基础知识
    'jvm': '/pages/jvm/jvm', //jvm
    'javaQuestionList': '/pages/javaQuestionList/javaQuestionList', //java知识列表
    'javaQuestionDetail': '/pages/javaQuestionDetail/javaQuestionDetail', //java知识详情
  },
  service: {
    getJavaQuestionAnswerById: `${baseUrl}/question/v1/admin/question/info`,
  },
  url: {
    images: `${env.staticDomain}/images`
  }
}

module.exports = config

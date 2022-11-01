const config = require('../../config')
const mock = require('../../mock')
const env = require('../../env')
const util = require('../../utils/util')
const restService = require('../../services/restService')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    javaQuestions: [],
    javaQuestionList: [],
    isLoading: true,
    imageUrl: config.url.images,
    type: '',
    title: ''
  },

  onLoad(e) {
    this.setData({
      type: e.type,
      title: e.title
    })
    wx.setNavigationBarTitle({
      title: e.title,
    })
  },
  
  onShow: function () {
    this.getJavaQuestionList(this.options.type)
  },

  onShareAppMessage() {
    // return custom share data when user share.
  },

  getJavaQuestionList: function (type) {
    restService.get(config.service.getJavaQuestionList + '/' + type).then((res) => {
      if (res && res.statusCode == 200) {
        const javaQuestions = JSON.parse(res.data).list
       
        this.setData({
          javaQuestions: javaQuestions,
          javaQuestionList: javaQuestions,
          isLoading: false,
        })
      } else {
        util.logAndTips("javaQuestionDetail:", "getJavaQuestionAnswerById2:", res)
      }
    }).catch((res) => {
      util.logAndTips("javaQuestionDetail:", "getJavaQuestionAnswerById2:", res)
    })
  },
  
  formatList: function (javaQuestions, type) {
    let javaQuestionList = []
    for (let javaQuestion of javaQuestions) {
      javaQuestionList.push({
        id: javaQuestion._id,
        imageUrl: this.getImageUrl(type),
        answerUrl: javaQuestion.answer,
        title: javaQuestion.question,
        subTitle: javaQuestion.subTitle ? javaQuestion.subTitle : ''
      })
    }
    return javaQuestionList
  },

  getImageUrl: function(type) {
    let imageUrl = '';
    switch(type) {
      case "javaBasic" : 
        imageUrl = "../../images/index/index_answer.png"
        break
      case "javaBasic" : 
        imageUrl = "../../images/index/icon_code.png"
        break
      default:
        imageUrl = "../../images/index/index_answer.png"
        break
    }
    return imageUrl;
  }
})
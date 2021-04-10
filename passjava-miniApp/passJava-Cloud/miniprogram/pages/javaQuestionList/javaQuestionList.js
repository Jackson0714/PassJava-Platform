const config = require('../../config')
const mock = require('../../mock')
const env = require('../../env')

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
    this.getJavaQuestionsList()
  },

  onShareAppMessage() {
    // return custom share data when user share.
  },

  getJavaQuestionsList: function () {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'getJavaQuestionList',
      data: {
        type: this.data.type
      }
    }).then(res => {
      let javaQuestions = res.result.data
      let javaQuestionList = this.formatList(javaQuestions, this.type)
      this.setData({
        javaQuestions: javaQuestions,
        javaQuestionList: javaQuestionList,
        isLoading: false,
      })
    }).catch(console.error)
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
const config = require('../../config')
const cssUtil = require('../../utils/cssUtil')
const util = require('../../utils/util')
const restService = require('../../services/restService')
//获取应用实例
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail:'',
    isLoading: true,
    article: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getJavaQuestionAnswerById(options.id) // 通过rest api获取
  },

  onShow:function(options) {
    
  },

  onUnload: function () {
  },

  getJavaQuestionAnswerById: function (javaQuestionId) {
    restService.get(config.service.getJavaQuestionAnswerById + '/' + javaQuestionId).then((res) => {
      if (res && res.statusCode == 200) {
        const data = JSON.parse(res.data)
        let markdownContent = this.convertToMarkdown(data.question.answer)
        this.setData({
          article: markdownContent,
          isLoading: false,
        })
      } else {
        util.logAndTips("javaQuestionDetail:", "getJavaQuestionAnswerById2:", res)
      }
    }).catch((res) => {
      util.logAndTips("javaQuestionDetail:", "getJavaQuestionAnswerById2:", res)
    })
  },

  convertToMarkdown: function(answerHtml) {
    let result = app.towxml(answerHtml, 'markdown',{
      theme:'light',					// 主题，默认`light`
      events:{					// 为元素绑定的事件方法
        tap:(e)=>{
          console.log('tap',e);
          var src = e.target.dataset.data.attr.src
          var tag = e.target.dataset.data.tag
          if (tag == "img") {
            wx.previewImage({
              current: src, // 当前显示图片的http链接  
              urls: [src] // 需要预览的图片http链接列表  
            })
          }
        }
      }
    });

    return result
  },

  onShareAppMessage() {
    // return custom share data when user share.
  },
  
})
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
    //let javaQuestionId = decodeURIComponent(options.id)
    let answerUrl = decodeURIComponent(options.answerUrl)
    //this.getJavaQuestionAnswerById2(javaQuestionId) // 通过rest api获取
    this.getJavaAnswerByAnswerUrl(answerUrl)
  },

  getJavaAnswerByAnswerUrl: function(answerUrl) {
    this.getMarkdownFile(answerUrl)
  },

  onShow:function(options) {
    
  },

  onUnload: function () {
  },

  getJavaQuestionAnswerById2: function (javaQuestionId) {
    let that = this
    let params = {
      id: javaQuestionId
    }

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

  getJavaQuestionAnswerById: function (javaQuestionId) {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'getJavaQuestionDetail',
      data: {
        id: javaQuestionId
      }
    }).then(res => {
      let answerHtml = res.result.answer
      //answerHtml = cssUtil.addStyle(answerHtml);
      answerHtml = cssUtil.convertToMarkdown(answerHtml)
      this.setData({
        isLoading: false,
        detail: answerHtml
      })

      //this.getMarkdownFile(answerUrl)
    }).catch(console.error)
  },
  
  getMarkdownFile(answerUrl) {
    wx.cloud.downloadFile({
      fileID: answerUrl,
      success: res => {
        // get temp file path
        let tempFilePath = res.tempFilePath
        console.log('downloadFile', tempFilePath)
        //let index = tempFilePath.indexOf("?")
        //tempFilePath = tempFilePath.substring(0, index)
        //console.log('index', index)
        console.log('tempFilePath', tempFilePath)
        try {
          let savedFile = wx.getFileSystemManager().saveFileSync(tempFilePath, wx.env.USER_DATA_PATH + "/local.md")
          console.log('savedFile',savedFile);
          let fileContent = wx.getFileSystemManager().readFileSync(savedFile, "utf-8", 0)
          console.log('savedFile',fileContent);
          let markdownContent = this.convertToMarkdown(fileContent)
          this.setData({
            article: markdownContent,
            isLoading: false,
            detail: fileContent
          })
          
        } catch (error) {
          console.log(error);
        }
        
      },
      fail: err => {
        console.log(err);
      }
    })
  },

  convertToMarkdown: function(answerHtml) {
    let result = app.towxml(answerHtml,'markdown',{
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
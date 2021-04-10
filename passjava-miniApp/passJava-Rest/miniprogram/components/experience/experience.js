const config = require('../../config')
const env = require('../../env')

Component({
  /**
   * 组件的属性列表
   * 用于组件自定义设置
   */
  properties: {
    //图片url
    coverUrl: {
      type: String,
      value: ''
    },
    //标题
    title: {
      type: String,
      value: ''
    },
    pagePath: {
      type: String,
      value: ''
    }
  },
  /**
   * 私有数据,组件的初始数据
   * 可用于模版渲染
   */
  data: {
  },
  /**
   * 组件的方法列表
   * 更新属性和数据的方法与更新页面数据的方法类似
   */
  methods: {
    gotoExperience: function () {
      wx.navigateTo({
        url: config.pages.webview + '?url=' + this.data.pagePath,
      })
    }
  }
})
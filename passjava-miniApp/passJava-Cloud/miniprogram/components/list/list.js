const config = require('../../config')

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    items: {
      type: Array,
      value: []
    },
    btnClick: String
  },

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: config.url.images,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    viewDetail(e) {
      const data = e.currentTarget.dataset
      const item = data.item
      let detailUrl = config.pages.javaQuestionDetail + '?id=' + item.id + '&answerUrl=' + item.answerUrl
      wx.navigateTo({
        url: detailUrl,
      })
    }
  }
})

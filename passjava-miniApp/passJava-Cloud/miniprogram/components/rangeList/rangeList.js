const config = require('../../config')

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    typeName: {
      type: String,
      valeu: ''
    },
    typeKey: {
      type: String,
      valeu: ''
    },
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
      let detailUrl = config.pages.dailyQuestionsWeb + '?url=' + item.url + '&title=' + item.title
      wx.navigateTo({
        url: detailUrl,
      })
    }
  }
})

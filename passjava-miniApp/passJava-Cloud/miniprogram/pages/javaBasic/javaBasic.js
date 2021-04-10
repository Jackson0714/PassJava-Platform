const config = require('../../config')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    url: '',
    showIndex: 0,
    faqs:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let url = decodeURIComponent(options.url)
    this.setData({
      url: url,
    })
  },

  onUnload: function () {
  },

  previewImage: function (e) {
    var current = e.target.dataset.src;
    wx.previewImage({
      current: current, // 当前显示图片的http链接  
      urls: [current] // 需要预览的图片http链接列表  
    })
  },

  onShareAppMessage() {
    // return custom share data when user share.
  },

  displayOrHiddenTopicDetail: function (e) {
    if (e.currentTarget.dataset.index != this.data.showIndex) {
      this.setData({
        showIndex: e.currentTarget.dataset.index
      })
    } else {
      this.setData({
        showIndex: 0
      })
    }
  }
  
})
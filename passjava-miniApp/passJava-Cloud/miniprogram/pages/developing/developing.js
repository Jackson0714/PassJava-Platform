const config = require('../../config')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    url: ''
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

  onShareAppMessage() {
    // return custom share data when user share.
  },
  
})
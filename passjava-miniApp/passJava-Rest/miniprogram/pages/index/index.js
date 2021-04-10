//index.js
const app = getApp()
const config = require('../../config')
const mock = require('../../mock')
const timeUtil = require('../../utils/timeUtil')
const env = require('../../env')

Page({
  data: {
    clock: [],
    isLoadingBanner: true,
    imageUrl: config.url.images,
    mockBanner: mock.banner,
    swiperCurrent: 0,
    userInfo: {},
    requestResult: '',
    isShowResigterModal: false
  },

  onPullDownRefresh: function () {
    wx.request({
      url: '',
      data: {},
      method: 'GET',
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) {
        wx.stopPullDownRefresh();
      }
    })
  },

  onLoad: function() {
    if (!wx.cloud) {
      wx.redirectTo({
        url: '../chooseLib/chooseLib',
      })
      return
    }

    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              this.setData({
                //isShowResigterModal: true //false
              })
            }
          })
          this.getMember()
        } else {
          this.setData({
            isShowResigterModal: true
          })
        }
      }
    })

    var interval = setInterval(function () {
      //将时间传如 调用
      let examTime = '2020/04/01 00:00:00';
      var clock = timeUtil.calculateTime(examTime.replace(/-/g, '/'));

      this.setData({//正常倒计时        
        clock: clock
      });
    }.bind(this), 1000);
  },

  onGetUserInfo: function (e) {
    if (!this.logged && e.detail.userInfo) {
      this.setData({
        logged: true,
        avatarUrl: e.detail.userInfo.avatarUrl,
        userInfo: e.detail.userInfo,
      })
      this.createMember(e.detail.userInfo)
    }
  },

  createMember: function (user) {
    // 创建会员
    wx.cloud.callFunction({
      name: 'member',
      data: {
        action: 'create',
        user: user
      },
      success: res => {
        console.log('[云函数] [login] user openid: ', res.result.openid)
        app.globalData.openid = res.result.openid
        app.globalData.openid = res.result.nickName
        this.setData({
          isShowResigterModal: false
        })
      },
      fail: err => {
        console.error('[云函数] [login] 调用失败', err)
      }
    })
  },

  getMember: function () {
    // 查询会员
    wx.cloud.callFunction({
      name: 'member',
      data: {
        action: 'get'
      },
      success: res => {
        console.log('[云函数] [login] res: ', res.result)
        if (res.result != null) {
          app.globalData.openid = res.result.openid
          app.globalData.memberId = res.result._id
          app.globalData.nickName = res.result.nickName
          this.setData({
            isShowResigterModal: false
          })
        } else {
          this.setData({
            isShowResigterModal: true
          })
        }
      },
      fail: err => {
        console.error('[云函数] [login] 调用失败', err)
      }
    })
  },

  onGetOpenid: function () {
    // 调用云函数
    wx.cloud.callFunction({
      name: 'login',
      data: {},
      success: res => {
        openid = res.result.openid
        console.log('[云函数] [login] user openid: ', openid)
        app.globalData.openid = openid
      },
      fail: err => {
        console.error('[云函数] [login] 调用失败', err)
      }
    })
  },

  /**
    * 生命周期函数--监听页面显示
    */
  onShow: function () {
    this.setData({
      selectedStatus: true, // 底部菜单选中状态
    })
    this.getBanner()
    this.getExperience()
    this.getSummaryList()
  },

  onShareAppMessage() {
    // return custom share data when user share.
  },

  gotoJavaBasic: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=javaBasic&title=Java基础'
    })
  },

  gotoJvm: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=jvm&title=Jvm&title=JVM'
    })
  },

  gotoSpring: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=spring&title=Spring'
    })
  },

  gotoMultiThreading: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=multiThreading&title=多线程'
    })
  },

  gotoMessageQueue: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=messageQueue&title=消息队列'
    })
  },

  gotoZookeeper: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=zookeeper&title=Zookeeper'
    })
  },

  gotoLog: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=log&title=日志'
    })
  },

  gotoInternet: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=internet&title=网络'
    })
  },

  gotoAlgorithm: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=algorithm&title=Java算法'
    })
  },

  gotoDataStructure: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=dataStructure&title=数据结构'
    })
  },

  gotoCloud: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=cloud&title=云计算'
    })
  },

  gotoMicroService: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=microService&title=微服务'
    })
  },

  gotoCache: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=cache&title=缓存'
    })
  },

  gotoBigData: function () {
    wx.navigateTo({
      url: config.pages.javaQuestionList + '?type=bigData&title=大数据'
    })
  },

  gotoDeveloping: function () {
    wx.navigateTo({
      url: config.pages.developing
    })
  },

  gotoSimulatedExam5: function () {
    let examUrl = config.pages.exam + '?type=simulated&value=5&title=模拟试题5'
    wx.navigateTo({
      url: examUrl
    })
  },

  getBanner: function () {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'getBanner',
    })
      .then(res => {
        this.setData({
          banners: res.result.data,
          isLoadingBanner: false,
        })
      }) .catch(console.error)
  },

  getExperience: function () {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'getExperience',
    })
      .then(res => {
        this.setData({
          experiences: res.result.data,
          isLoadingBanner: false,
        })
      }).catch(console.error)
  },

  getSummaryList: function () {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'getSummaryList',
    })
      .then(res => {
        let summaryList = this.formatSummaryList(res.result.data)
        this.setData({
          summaryList: summaryList,
          isLoadingBanner: false,
        })
      }).catch(console.error)
  },

  formatSummaryList: function (summaryList) {
    let formatedSummaryList = []
    for (let summary of summaryList) {
      formatedSummaryList.push({
        id: summary.id,
        url: env.pmpDocUrl + summary.pagePath,
        imageUrl: summary.imageUrl,
        answerUrl: summary.answer,
        title: summary.title,
        subTitle: summary.subTitle ? summary.subTitle : ''
      })
    }
    return formatedSummaryList
  },

  add: function () {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'add',
      // 传给云函数的参数
      data: {
        a: 1,
        b: 2,
      },
    })
      .then(res => {
        console.log(res.result) // 3
      })
      .catch(console.error)
  },

  swiperChange: function (e) {
    this.setData({
      swiperCurrent: e.detail.current
    })
  },
})

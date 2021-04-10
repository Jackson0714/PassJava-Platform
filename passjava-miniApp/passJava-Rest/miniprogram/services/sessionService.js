const session = {
  get: (key) => {
    return wx.getStorageSync(key) || null;
  },
  set: (key, session) => {
    wx.setStorageSync(key, session);
  },
  remove: (key) => {
    wx.removeStorageSync(key)
  },
  clear: () => {
    wx.clearStorageSync()
  },
}

module.exports = session;
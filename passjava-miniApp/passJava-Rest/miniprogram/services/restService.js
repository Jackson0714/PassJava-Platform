const sessionService = require('./sessionService')

const getDefaultOptions = () => {
  return {
    header: {
      'Authorization': 'Bearer ' + sessionService.get('accessToken')
    },
    dataType: {
      'content-type': 'application/json'
    }
  }
}

const request = (url, method, params) => {
  const defaultOptions = getDefaultOptions()

  return new Promise((resolve, reject) => {
    let options = {
      url: url,
      method: method,
      data: params,
      success(res) {
        resolve(res)
      },
      fail(err) {
        reject(err)
      }
    }
    options = Object.assign({}, defaultOptions, options)
    wx.request(options)
  })
}

module.exports = {
  get: (url, params) => {
    return request(url, 'get', params)
  },

  post: (url, params) => {
    return request(url, 'post', params)
  },

  put: (url, params) => {
    return request(url, 'delete', params)
  },

  delete: (url, params) => {
    return request(url, 'put', params)
  }
}

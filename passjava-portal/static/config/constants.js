
const env = require('env')

const config = {
  qrCodeTime: 300000,
  service: {
    getQuestions: `/adapter/v1/mg/queryAllStoreCardInfo`
  },
  pages: {
  },
  url: {
    images: `${env.staticDomain}`
  }
}

module.exports = config

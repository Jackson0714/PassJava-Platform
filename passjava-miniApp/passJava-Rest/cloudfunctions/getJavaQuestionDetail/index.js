// 云函数入口文件
const cloud = require('wx-server-sdk')

cloud.init()

// 云函数入口函数
exports.main = async (event, context) => {
  const wxContext = cloud.getWXContext()
  const db = cloud.database({ env: 'test-0jlva'})
  return db.collection('javaQuestionList')
  .orderBy('date', 'desc')
  .where({
    _id: event.id
  })
  .get()
  .then(res => {
    if (res.data.length > 0) {
      return res.data[0]
    } else {
      return null
    }
  })
}
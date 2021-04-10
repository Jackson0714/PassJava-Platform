  // 云函数入口文件
const cloud = require('wx-server-sdk')

cloud.init()

// 云函数入口函数
exports.main = async (event, context) => {
  switch (event.action) {
    case 'create': {
      return create(event, event.user)
    }
    case 'get': {
      return get(event, event.openid)
    }
    default: {
      return
    }
    return
  }
}

async function create(event, user) {
  const wxContext = cloud.getWXContext()
  const db = cloud.database(
    {
      env: 'test-0jlva'
    }
  )
  //1.查询会员是否存在
  //2.不存在则创建会员
  result = db.collection('member').where({
    openid: wxContext.OPENID // 填入当前用户 openid
  }).get().then(res => {
    if (res.data.length == 0) {
      member = db.collection('member').add({
        // data 字段表示需新增的 JSON 数据
        data: {
          nickName: user.nickName,
          avatarUrl: user.avatarUrl,
          gender: user.gender,
          country: user.country,
          province: user.province,
          city: user.city,
          openid: wxContext.OPENID,
          appid: wxContext.APPID,
          unionid: wxContext.UNIONID,
        }
      }).then(res => {
        console.log(res)
        return {
          event,
          user,
          openid: wxContext.OPENID,
          appid: wxContext.APPID,
          unionid: wxContext.UNIONID,
          memberId: res._id
        }
      }).catch(console.error)
      return member
    } else {
      member = res.data[0]
      return member
    }
    return member
  })
  
  return result
}

async function get(event) {
  const wxContext = cloud.getWXContext()
  const db = cloud.database(
    {
      env: 'test-0jlva'
    }
  )
  //查询会员
  result = db.collection('member').where({
    openid: wxContext.OPENID // 填入当前用户 openid
  }).get().then(res => {
    if (res.data.length > 0) {
      return res.data[0]
    } else {
      return null
    }
  })

  return result
}
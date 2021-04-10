const aes = require('aes.js')
const constants = require('../constant')

// 加载提示
const showBusy = text => wx.showToast({
  title: text,
  icon: 'loading',
  duration: 2000
})

// 成功提示
const showSuccess = text => wx.showToast({
  title: text,
  icon: 'success'
})

// 普通提示
const showTip = text => wx.showToast({
  title: text,
  icon: 'none',
  duration: 2000
})

//  手机号验证
const checkTel = (phone) => {
  let myreg = /^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$/;
  if (phone.length == 0) {
    wx.showToast({
      title: '请输入手机号码',
      icon: 'none'
    })
    return false;
  } else if (!myreg.test(phone)) {
    wx.showToast({
      title: '手机号格式不正确',
      icon: 'none'
    })
    return false;
  } else if (phone.length < 11) {
    wx.showToast({
      title: '手机号长度有误！',
      icon: 'none'
    })
    return false;
  }
  return true;
}

// 函数节流，在一定时间内只执行一次函数
const throttle = (fn, gapTime) => {
  if (gapTime == null || gapTime == undefined) {
    gapTime = 1000
  }

  let lastTime = null
  return function () {
    const that = this
    let nowTime = new Date().getTime()
    if (nowTime - lastTime > gapTime || !lastTime) {
      fn(that)
      lastTime = nowTime
    }
  }
}

// 函数节流，在一定时间内只执行一次函数只在套餐支付使用
const throttleForPay = (fn, gapTime) => {
  if (gapTime == null || gapTime == undefined) {
    gapTime = 1000
  }

  let lastTime = null
  return function () {
    const that = this
    let nowTime = new Date().getTime()
    if (nowTime - lastTime > gapTime || !lastTime) {
      fn(that)
      lastTime = nowTime
    } else {
      wx.showToast({
        title: '你的操作过于频繁，请稍候再试~',
        icon: 'none'
      })
    }
  }
}

// 对象复制
function copy(oldObject) {
  let newObject = {}
  newObject = JSON.parse(JSON.stringify(oldObject))
  return newObject
}

//千位置符号分割
const miliFormat = (num) => {
  return num && num.toString().replace(/^\d+/, (m) => m.replace(/(?=(?!^)(\d{3})+$)/g, ','))
}

function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('-')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

// 加密参数
function encrypt(key, param) {
  var str = aes.CryptoJS.enc.Utf8.parse(param);
  var key = aes.CryptoJS.enc.Utf8.parse(key);
  var encrypted = aes.CryptoJS.AES.encrypt(str, key, {
    mode: aes.CryptoJS.mode.ECB,
    padding: aes.CryptoJS.pad.Pkcs7
  });
  encrypted = encrypted.toString()
  encrypted = encrypted.replace(/\+/g, '-').replace(/\//g, '_')
  return encrypted;
}

// 解密参数
function decrypt(key, word) {
  var key = aes.CryptoJS.enc.Utf8.parse(key);
  word = word.replace(/\-/g, '+').replace(/\_/g, '/')
  var decrypt = aes.CryptoJS.AES.decrypt(word, key, {
    mode: aes.CryptoJS.mode.ECB,
    padding: aes.CryptoJS.pad.Pkcs7
  });
  var decryptedStr = decrypt.toString(aes.CryptoJS.enc.Utf8);
  return decryptedStr.toString();
}

// 价钱切割
function splitPrice (inputPrice) {
  let price = inputPrice || '0'
  let decimalBeforeText = price.toString().split('.')[0]
  let decimalAfterText = price.toString().split('.')[1]
  if (!decimalAfterText || decimalAfterText == '0' || decimalAfterText == '00') {
    decimalAfterText = ''
  } else if (decimalAfterText.endsWith('0')) {
    decimalAfterText = '.' + decimalAfterText.substring(0, 1)
  } else {
    decimalAfterText = '.' + decimalAfterText
  }
  return {
    decimalBeforeText: decimalBeforeText,
    decimalAfterText: decimalAfterText
  }
}

function compareVersion (v1, v2) {
  v1 = v1.split('.')
  v2 = v2.split('.')
  const len = Math.max(v1.length, v2.length)

  while (v1.length < len) {
    v1.push('0')
  }
  while (v2.length < len) {
    v2.push('0')
  }

  for (let i = 0; i < len; i++) {
    const num1 = parseInt(v1[i])
    const num2 = parseInt(v2[i])

    if (num1 > num2) {
      return 1
    } else if (num1 < num2) {
      return -1
    }
  }

  return 0
}

function logAndTips (filename, functionName, res) {
  console.log(filename, functionName, res)
  wx.showToast({
    title: constants.SYSTEM_ERROR_TIP,
    icon: 'none'
  })
}

function changeHtml(htmlText) {
  //清除宽度
  let clearWidth = htmlText.replace(/width="(\d*)"/g, "")
  //清除高度
  let tempText = clearWidth.replace(/height="(\d*)"/g, "")
  //清除特殊字符
  let clearNbsp = tempText.replace(/&nbsp;/g, " ");   //去掉&nbsp;
  let clearQuot = clearNbsp.replace(/&quot;/g, " ");   //去掉&quot;
  let clearGt = clearQuot.replace(/&gt;/g, " ");   //去掉&gt;
  let clearLt = clearGt.replace(/&lt;/g, " ");   //去掉&lt;
  let clearEnter = clearLt.replace(/[\r\n]/g, "");   //去掉所有的↵符号
  return clearEnter.replace(/\s*src/g, " style='width: 100%;' mode='aspectFill' src")
}

function showNoDataTip () {
  wx.showToast({
    title: '已经到底啦',
    icon: 'none',
    duration: 2000
  })
}

function getCurrentTime () {
  var myDate = new Date();
  var y=myDate.getFullYear();    //获取完整的年份(4位,1970-????)
  var m=myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
  var d=myDate.getDate();        //获取当前日(1-31)
  var t=myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
  var h=myDate.getHours();       //获取当前小时数(0-23)
  var min=myDate.getMinutes();     //获取当前分钟数(0-59)
  var s=myDate.getSeconds();     //获取当前秒数(0-59)
  var ms=myDate.getMilliseconds();    //获取当前毫秒数(0-999)
  if (m >= 1 && m <= 9) {
    m = "0" + m;
  }
  if (d >= 0 && d <= 9) {
    d = "0" + d;
  }
  if (h >= 0 && h <= 9) {
    h = "0" + h;
  }
  if (min >= 0 && min <= 9) {
    min = "0" + min;
  }
  if (s >= 0 && s <= 9) {
    s = "0" + s;
  }
  var currentTime = y + m + d + h + min + s + ms; //当前时间
  return currentTime
}

function showToTopTip () {
  wx.showToast({
    title: '已经到顶啦',
    icon: 'none',
    duration: 2000
  })
}

function getPhoneSystem () {
  var isIOS = false
  wx.getSystemInfo({
    success: function(res) {
      if (res.system.indexOf("iOS") >= 0) {
        isIOS = true
      }
    }
  })
  return isIOS
}

module.exports = {
  showBusy,
  showSuccess,
  checkTel,
  throttle,
  throttleForPay,
  copy,
  showTip,
  miliFormat,
  formatTime,
  encrypt,
  decrypt,
  splitPrice,
  compareVersion,
  logAndTips,
  changeHtml,
  showNoDataTip,
  getCurrentTime,
  showToTopTip,
  getPhoneSystem
}

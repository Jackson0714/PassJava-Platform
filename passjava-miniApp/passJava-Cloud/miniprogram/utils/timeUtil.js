//倒计时
const calculateTime = (endTime) => {
  var clockarr = [];
  endTime = Date.parse(endTime) / 1000;
  var timenow = Date.parse(new Date()) / 1000;

  var totalSecond = endTime - timenow;
  if (totalSecond < 0) {
    var time = '已结束';
  } else {
    var time = dateformat(totalSecond);
  }
  clockarr.push(time);

  return clockarr;
}

// 时间格式化输出，将时间戳转为 倒计时时间
const dateformat = (totalSecond) => {
  var second = totalSecond;//总的秒数
  // 天数位   
  var day = Math.floor(second / 3600 / 24);
  var dayStr = day.toString();
  if (dayStr.length == 1) dayStr = '0' + dayStr;

  // 小时位   
  var hr = Math.floor(second / 3600 % 24);
  //var hr = Math.floor(second / 3600);  //直接转为小时 没有天 超过1天为24小时以上

  var hrStr = hr.toString();
  if (hrStr.length == 1) hrStr = '0' + hrStr;

  // 分钟位  
  var min = Math.floor(second / 60 % 60);
  var minStr = min.toString();
  if (minStr.length == 1) minStr = '0' + minStr;

  // 秒位  
  var sec = Math.floor(second % 60);
  var secStr = sec.toString();
  if (secStr.length == 1) secStr = '0' + secStr;

  return dayStr + "天 " + hrStr + ":" + minStr + ":" + secStr;
}

// 计算所用时间
const calculateUsedTime = (endTime) => {
  var clockarr = [];
  var timenow = Date.parse(new Date()) / 1000;

  var totalSecond = timenow - endTime;
  if (totalSecond < 0) {
    var time = '已结束';
  } else {
    var time = dateformatUsedTime(totalSecond);
  }
  clockarr.push(time);

  return clockarr;
}

// 时间格式化输出，将时间戳转为 倒计时时间
const dateformatUsedTime = (totalSecond) => {
  var second = totalSecond;//总的秒数

  // 小时位   
  var hr = Math.floor(second / 3600 % 24);
  //var hr = Math.floor(second / 3600);  //直接转为小时 没有天 超过1天为24小时以上

  var hrStr = hr.toString();
  if (hrStr.length == 1) hrStr = '0' + hrStr;

  // 分钟位  
  var min = Math.floor(second / 60 % 60);
  var minStr = min.toString();
  if (minStr.length == 1) minStr = '0' + minStr;

  // 秒位  
  var sec = Math.floor(second % 60);
  var secStr = sec.toString();
  if (secStr.length == 1) secStr = '0' + secStr;

  return hrStr + ":" + minStr + ":" + secStr;
}

module.exports = { calculateTime, calculateUsedTime }
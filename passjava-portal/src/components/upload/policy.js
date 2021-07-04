import http from '@/utils/httpRequest.js'
export function policy () {
  return new Promise((resolve) => {
    http({
      url: http.adornUrl('/thirdparty/v1/admin/oss/getPolicy'),
      method: 'get',
      params: http.adornParams({})
    }).then(({ data }) => {
      resolve(data)
    })
  })
}

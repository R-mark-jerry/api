import request from '@/utils/request'

// 查询API参数列表
export function listParam(query) {
  return request({
    url: '/api/manage/param/list',
    method: 'get',
    params: query
  })
}

// 查询API参数详细
export function getParam(paramId) {
  return request({
    url: '/api/manage/param/' + paramId,
    method: 'get'
  })
}

// 根据API ID查询参数列表
export function listParamByApiId(apiId) {
  return request({
    url: '/api/manage/param/api/' + apiId,
    method: 'get'
  })
}

// 新增API参数
export function addParam(data) {
  return request({
    url: '/api/manage/param',
    method: 'post',
    data: data
  })
}

// 修改API参数
export function updateParam(data) {
  return request({
    url: '/api/manage/param',
    method: 'put',
    data: data
  })
}

// 删除API参数
export function delParam(paramId) {
  return request({
    url: '/api/manage/param/' + paramId,
    method: 'delete'
  })
}

// 批量删除API参数
export function delParams(paramIds) {
  return request({
    url: '/api/manage/param/' + paramIds,
    method: 'delete'
  })
}

// 根据API ID删除参数
export function delParamByApiId(apiId) {
  return request({
    url: '/api/manage/param/api/' + apiId,
    method: 'delete'
  })
}

// 批量新增API参数
export function addParams(data) {
  return request({
    url: '/api/manage/param/batch',
    method: 'post',
    data: data
  })
}

// 批量更新API参数
export function updateParams(data) {
  return request({
    url: '/api/manage/param/batch',
    method: 'put',
    data: data
  })
}

// 刷新API参数
export function refreshParams(apiId, data) {
  return request({
    url: '/api/manage/param/refresh/' + apiId,
    method: 'post',
    data: data
  })
}